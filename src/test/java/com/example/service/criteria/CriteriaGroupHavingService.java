package com.example.service.criteria;

import com.example.domain.Member;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * Created by jarvis on 16. 1. 3..
 */

@Service
public class CriteriaGroupHavingService {

    @PersistenceContext
    EntityManager em;

    public List<Object[]> getMembersWithOldestAndYoungest(){
        CriteriaBuilder cb = em.getCriteriaBuilder();
        final CriteriaQuery<Object[]> cq = cb.createQuery(Object[].class);
        final Root<Member> m = cq.from(Member.class);

        final Expression<Integer> maxAge = cb.max(m.<Integer>get("age"));
        final Expression<Integer> minAge = cb.min(m.<Integer>get("age"));

        cq.multiselect(m.get("team").get("name"), maxAge, minAge);
        cq.groupBy(m.get("team").get("name"));

        final TypedQuery<Object[]> query = em.createQuery(cq);
        return query.getResultList();
    }

    public List<Object[]> getMembersYoungestOlderThan10InTeam(){
        CriteriaBuilder cb = em.getCriteriaBuilder();
        final CriteriaQuery<Object[]> cq = cb.createQuery(Object[].class);
        final Root<Member> m = cq.from(Member.class);

        final Expression<Integer> maxAge = cb.max(m.<Integer>get("age"));
        final Expression<Integer> minAge = cb.min(m.<Integer>get("age"));

        cq.multiselect(m.get("team").get("name"), maxAge, minAge);
        cq.groupBy(m.get("team").get("name"));
        cq.having(cb.gt(minAge, 10));

    final TypedQuery<Object[]> query = em.createQuery(cq);
    return query.getResultList();
    }
}
