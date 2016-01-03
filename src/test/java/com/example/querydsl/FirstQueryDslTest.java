package com.example.querydsl;

import com.example.domain.Member;
import com.example.domain.QMember;
import com.example.domain.Team;
import com.example.jpql.TestJPQLConfig;
import com.example.repository.MemberRepository;
import com.mysema.query.jpa.impl.JPAQuery;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Arrays;
import java.util.List;

/**
 * Created by arahansa on 2016-01-03.
 */
public class FirstQueryDslTest extends TestJPQLConfig{

    @PersistenceContext
    EntityManager em;
    @Autowired
    MemberRepository memberRepository;

    @Before
    public void setup(){
        // save
        Team team1 = new Team("팀A");
        Team team2 = new Team("팀B");

        List<Member> members = Arrays.asList(
                new Member("아라한사", team1, 30), new Member("임형주", team1, 20),
                new Member("류형석", team1, 15), new Member("캣츠컬", team1, 12),
                new Member("수지", team2, 40), new Member("강백호", team2, 15),
                new Member("복덩어리", team2, 25), new Member("방패연", team2, 1)
        );
        memberRepository.save(members);
    }

    @Test
    public void queryDsl() throws Exception{
        JPAQuery query = new JPAQuery(em);
        QMember qMember = new QMember("m");
        List<Member> members = query.from(qMember)
                .where(qMember.username.eq("아라한사"))
                .orderBy(qMember.age.desc())
                .list(qMember);
        System.out.println(" Query DSL 로 찾은 아라한사 ");
        members.forEach(n-> System.out.println(n));
    }

}
