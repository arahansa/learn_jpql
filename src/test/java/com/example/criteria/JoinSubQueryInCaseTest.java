package com.example.criteria;

import com.example.domain.Member;
import com.example.domain.Team;
import com.example.jpql.TestJPQLConfig;
import com.example.repository.MemberRepository;
import com.example.service.criteria.CriteriaJoinSubqueryInCaseService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import static org.junit.Assert.*;
/**
 * Created by jarvis on 16. 1. 3..
 */
@Transactional
public class JoinSubQueryInCaseTest extends TestJPQLConfig{

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    CriteriaJoinSubqueryInCaseService joinSubqueryInCaseService;

    @Before
    public void setup(){
        // save
        Team team1 = new Team("팀A");
        Team team2 = new Team("팀B");

        List<Member> members = Arrays.asList(
                new Member("아라한사", team1, 30), new Member("임형주", team1, 20),
                new Member("수지", team2), new Member("강백호", team2)
        );
        memberRepository.save(members);
    }

    @Test
    public void testJoin() throws Exception{
        System.out.println("조인으로 얻어오기");
        final List<Object[]> memberTeam = joinSubqueryInCaseService.getMemberTeam("팀A");
        for(Object[] row : memberTeam){
            assertTrue(row[0] instanceof Member);
            assertTrue(row[1] instanceof Team);
        }
    }

}
