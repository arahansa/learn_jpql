package com.example.jpql;

import com.example.LearnJpqlApplication;
import com.example.domain.Member;
import com.example.domain.Team;
import com.example.service.jpql.JoinService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by jarvis on 15. 12. 27..
 */
public class JoinTest extends TestJPQLConfig{

    @Autowired
    JoinService joinService;

    @Test
    public void joinTest() throws Exception{
        // save
        Team team1 = new Team("팀A");
        Team team2 = new Team("팀B");

        List<Member> members = Arrays.asList(
                new Member("아라한사", team1, 30), new Member("임형주", team1, 20),
                new Member("수지", team2), new Member("강백호", team2)
        );
        memberRepository.save(members);

        // inner join
        System.out.println("조인으로 팀A인 멤버들 구하기");
        final List<Member> memberJoin = joinService.getMemberJoin();
        System.out.println("흐음?");
        assertEquals(2, memberJoin.size());
        Member getMember = memberJoin.get(0);
        assertEquals("아라한사", getMember.getUsername());
        assertEquals("팀A", getMember.getTeam().getName());

        // outer join
        System.out.println("outerJoin 쿼리 보기! ");
        final List<Member> memberJoinOuter = joinService.getMemberJoinOuter();
        assertEquals(2, memberJoinOuter.size());

        // fetch join
        System.out.println("페치조인 쿼리 보기 ");
        final List<Member> memberJoinFetch = joinService.getMemberJoinFetch();
        assertEquals(2, memberJoinFetch.size());
    }
}
