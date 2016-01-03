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
}
