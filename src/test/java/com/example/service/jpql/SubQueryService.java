package com.example.service.jpql;

import com.example.domain.Member;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Created by jarvis on 15. 12. 31..
 */
@Transactional
@Service
public class SubQueryService {


    @PersistenceContext
    EntityManager em;

    public List<Member> getMembersOlderThanAvg(){
        String q ="select m from Member m where m.age > (select avg(m2.age) from Member m2)";
        final TypedQuery<Member> memberTypedQuery = em.createQuery(q, Member.class);
        return memberTypedQuery.getResultList();
    }

    public List<Member> getMemberWithParam(String jpql){
        final TypedQuery<Member> memberTypedQuery = em.createQuery(jpql, Member.class);
        return memberTypedQuery.getResultList();
    }


}
