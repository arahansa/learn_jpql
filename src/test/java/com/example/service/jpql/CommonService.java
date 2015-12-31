package com.example.service.jpql;

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
public class CommonService {

    @PersistenceContext
    EntityManager em;

    public <T> List<T> getClassTypeWithParam(String jpql, Class clazz){
        final TypedQuery<T> memberTypedQuery = em.createQuery(jpql, clazz);
        return memberTypedQuery.getResultList();
    }
}
