package com.example.service.jpql;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Created by arahansa on 2016-01-02.
 */
@Transactional
@Service
public class PolyService extends CommonService{

    public List getCars(){
        return  em.createQuery("select c from Car c").getResultList();
    }

    public List getCars(String jpql){
        return  em.createQuery(jpql).getResultList();
    }

}
