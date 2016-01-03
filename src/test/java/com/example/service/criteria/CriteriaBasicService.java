package com.example.service.criteria;

import com.example.domain.Member;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.List;

/**
 * Created by arahansa on 2016-01-03.
 */
@Service
public class CriteriaBasicService {

    @PersistenceContext
    EntityManager em;

    public List<Member> getMembers(){
        CriteriaBuilder cb = em.getCriteriaBuilder();
        final CriteriaQuery<Member> cq = cb.createQuery(Member.class);
        final Root<Member> m = cq.from(Member.class);
        cq.select(m);

        final TypedQuery<Member> query = em.createQuery(cq);
        return query.getResultList();
    }

    public List<Member> getMembersWithUsernameCondition(String username){
        CriteriaBuilder cb = em.getCriteriaBuilder();
        final CriteriaQuery<Member> cq = cb.createQuery(Member.class);
        final Root<Member> m = cq.from(Member.class);

        final Predicate usernameEqual = cb.equal(m.get("username"), username);
        javax.persistence.criteria.Order ageDesc = cb.desc(m.get("age"));

        cq.select(m).where(usernameEqual).orderBy(ageDesc);
        return em.createQuery(cq).getResultList();
    }
    public List<Member> getMemberOlderthan10(){
        CriteriaBuilder cb = em.getCriteriaBuilder();
        final CriteriaQuery<Member> cq = cb.createQuery(Member.class);
        final Root<Member> m = cq.from(Member.class);

        final Predicate ageGt = cb.greaterThan(m.<Integer>get("age"), 10);

        cq.select(m).where(ageGt).orderBy(cb.desc(m.get("age")));
        return em.createQuery(cq).getResultList();
    }

    public List<Member> getMembersWithManySelect(){
        CriteriaBuilder cb = em.getCriteriaBuilder();
        final CriteriaQuery<Member> cq = cb.createQuery(Member.class);
        final Root<Member> m = cq.from(Member.class);

        cq.multiselect(m.get("username"), m.get("age"));
        return em.createQuery(cq).getResultList();
    }


    public List<Member> getMembersDistinct() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        final CriteriaQuery<Member> cq = cb.createQuery(Member.class);
        final Root<Member> m = cq.from(Member.class);

        cq.multiselect(m.get("username"), m.get("age")).distinct(true);
        return em.createQuery(cq).getResultList();
    }


    public List<Tuple> getMemberWithTuple(){
        CriteriaBuilder cb = em.getCriteriaBuilder();
        final CriteriaQuery<Tuple> cq = cb.createTupleQuery();
        final Root<Member> m = cq.from(Member.class);
        cq.multiselect(
                m.get("username").alias("username"),
                m.get("age").alias("age")
        ).orderBy(cb.desc(m.get("age")));

        final TypedQuery<Tuple> query = em.createQuery(cq);
        final List<Tuple> resultList = query.getResultList();
        return resultList;
    }
}
