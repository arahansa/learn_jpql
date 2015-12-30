package com.example.service.jpql;

import com.example.domain.Member;
import com.example.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Created by jarvis on 15. 12. 27..
 */
@Transactional
@Service
public class JoinService {

    @Autowired
    MemberRepository memberRepository;
    @PersistenceContext
    EntityManager em;


    public List<Member> getMemberJoin(){
        String teamName = "팀A";
        String query = "SELECT m from Member m INNER JOIN m.team t WHERE t.name=:teamName order by m.age desc";
        final TypedQuery<Member> typedQuery = em.createQuery(query, Member.class);
        typedQuery.setParameter("teamName", teamName);
        return typedQuery.getResultList();
    }

    public List<Member> getMemberJoinOuter(){
        String teamName = "팀A";
        String query = "SELECT m from Member m LEFT JOIN m.team t WHERE t.name=:teamName order by m.age desc";
        final TypedQuery<Member> typedQuery = em.createQuery(query, Member.class);
        typedQuery.setParameter("teamName", teamName);
        return typedQuery.getResultList();
    }

    public List<Member> getMemberJoinFetch() {
        String teamName = "팀A";
        String query = "SELECT m from Member m join fetch m.team t WHERE t.name=:teamName order by m.age desc";
        final TypedQuery<Member> typedQuery = em.createQuery(query, Member.class);
        typedQuery.setParameter("teamName", teamName);
        return typedQuery.getResultList();
    }
}
