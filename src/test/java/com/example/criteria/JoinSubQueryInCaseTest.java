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
                new Member("수지", team2, 10), new Member("강백호", team2, 40)
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
    
    @Test
    public void subQueryTest() throws Exception{
        System.out.println("서브쿼리 얻어오기 ( 평균 나이 ) ");
        final List<Member> memberSubQuery = joinSubqueryInCaseService.getMemberSubQuery();
        assertEquals(2, memberSubQuery.size());
    }
    
    @Test
    public void subQueryIntercorrelate() throws Exception{
        System.out.println("서브쿼리 상호관련 : 팀A에 포함된");
        final List<Member> memberIntercorrelateSubQuery = joinSubqueryInCaseService.getMemberIntercorrelateSubQuery("팀A");
        memberIntercorrelateSubQuery.forEach(n-> System.out.println(n));
        memberIntercorrelateSubQuery.forEach(n->assertEquals("팀A", n.getTeam().getName()));
    }
    
    @Test
    public void inStatement() throws Exception{
        System.out.println(" In statement ");
        final List<Member> memberInStatement = joinSubqueryInCaseService.getMemberInStatement("아라한사", "수지");
        assertEquals(30, memberInStatement.get(0).getAge());
        assertEquals("아라한사", memberInStatement.get(0).getUsername());
        assertEquals(10, memberInStatement.get(1).getAge());
        assertEquals("수지", memberInStatement.get(1).getUsername());
    }
    
    @Test
    public void getUserNameAndMoneyWithCaseStatement() throws Exception{
        System.out.println(" case 문 보기 ");
        final List<Object[]> memberCase = joinSubqueryInCaseService.getMemberCase();
        // 40 30 20 10  . 강백호 아라한사 임형주 수지
        for(Object[] rows : memberCase){
            System.out.println("이름 : "+rows[0] +", 금액 : "+rows[1]);
        }
    }



}
