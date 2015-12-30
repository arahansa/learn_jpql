package com.example.service.jpql;

import com.example.domain.Member;
import com.example.domain.Team;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Created by jarvis on 15. 12. 30..
 */
@Transactional
@Service
public class PathExpressionService {

    @PersistenceContext
    EntityManager em;

    public List<Team> getTeamFromPathExpression(){
        String jpql = "select o.member.team from Order o where o.product.name = 'productA' and o.address.city = 'JINJU' order by o.member.age desc";
        final TypedQuery<Team> typedQuery = em.createQuery(jpql, Team.class);
        return typedQuery.getResultList();
    }

}
