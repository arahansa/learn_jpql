package com.example.service.criteria;

import com.example.domain.Member;
import com.example.domain.Team;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.List;

/**
 * Created by jarvis on 16. 1. 3..
 */
@Service
public class CriteriaJoinSubqueryInCaseService {

    @PersistenceContext
    EntityManager em;

    public List<Object[]> getMemberTeam(String teamName){

        CriteriaBuilder cb = em.getCriteriaBuilder();
        final CriteriaQuery<Object[]> cq = cb.createQuery(Object[].class);
        final Root<Member> m = cq.from(Member.class);

        Join<Member, Team> t = m.join("team", JoinType.INNER);

        cq.multiselect(m, t).where(cb.equal(t.get("name"), teamName));

        final TypedQuery<Object[]> query = em.createQuery(cq);
        return query.getResultList();
    }

    public List<Member> getMemberSubQuery(){
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Member> mainQuery = cb.createQuery(Member.class);

        // 서브쿼리 생성
        Subquery<Double> subQuery = mainQuery.subquery(Double.class);
        Root<Member> m2 = subQuery.from(Member.class);
        subQuery.select(cb.avg(m2.<Integer>get("age")));

        Root<Member> m = mainQuery.from(Member.class);
        mainQuery.select(m).where(cb.ge(m.<Integer>get("age"), subQuery)).orderBy(cb.desc(m.get("age")));
        return em.createQuery(mainQuery).getResultList();
    }

    public List<Member> getMemberIntercorrelateSubQuery(String teamName){
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Member> mainQuery = cb.createQuery(Member.class);

        // 서브쿼리에서 사용되는 메인쿼리의 m
        Root<Member> m = mainQuery.from(Member.class);

        // 서브쿼리 생성
        Subquery<Team> subQuery = mainQuery.subquery(Team.class);
        Root<Member> subM = subQuery.correlate(m);  // 메인쿼리의 별칭을 가져옴
        Join<Member, Team> t = subM.join("team");
        subQuery.select(t).where(cb.equal(t.get("name"), teamName));

        // 메인쿼리 생성
        mainQuery.select(m).where(cb.exists(subQuery));
        return em.createQuery(mainQuery).getResultList();
    }

    public List<Member> getMemberInStatement(String member1, String member2){
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Member> cq = cb.createQuery(Member.class);
        Root<Member> m = cq.from(Member.class);

        cq.select(m).where(cb.in(m.get("username")).value(member1).value(member2)).orderBy(cb.desc(m.get("age")));
        return em.createQuery(cq).getResultList();
    }

    public List<Object[]> getMemberCase(){

        CriteriaBuilder cb = em.getCriteriaBuilder();
        final CriteriaQuery<Object[]> cq = cb.createQuery(Object[].class);
        final Root<Member> m = cq.from(Member.class);

        cq.multiselect(
                m.get("username"),
                cb.selectCase()
                        .when(cb.ge(m.<Integer>get("age"), 30), 600)
                        .when(cb.le(m.<Integer>get("age"), 15), 500)
                        .otherwise(1000)
        ).orderBy(cb.desc(m.get("age")));
        final TypedQuery<Object[]> query = em.createQuery(cq);
        return query.getResultList();
    }
}
