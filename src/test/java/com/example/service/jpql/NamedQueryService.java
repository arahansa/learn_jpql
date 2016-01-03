package com.example.service.jpql;

import com.example.domain.Member;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by arahansa on 2016-01-03.
 */
@Transactional
@Service
public class NamedQueryService {
    @PersistenceContext
    EntityManager em;

    public List<Member> getMembersByUserName(String username){
        List<Member> resultList = em.createNamedQuery("Member.findByUsername", Member.class)
                .setParameter("username", username).getResultList();
        return resultList;
    }

    public long getMemberCount(){
        final Object count = em.createNamedQuery("Member.count").getSingleResult();
        return (long) count;
    }
}
