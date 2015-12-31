package com.example.jpql;

import com.example.domain.*;
import com.example.repository.OrderRepository;
import com.example.service.jpql.CommonService;
import com.example.service.jpql.PathExpressionService;
import com.example.service.jpql.SubQueryService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by jarvis on 15. 12. 31..
 */
public class SubQuery_Condition_Test extends TestJPQLConfig {

    @Autowired
    SubQueryService subQueryService;
    String bq = "select m from Member m ";

    @Test
    public void 서브쿼리_조건식테스트(){

        String 나이가_평균보다_많은회원=bq+"where m.age > (select avg(m2.age) from Member m2)";
        final List<Member> olderMemberThanAvg = subQueryService.getMemberWithParam(나이가_평균보다_많은회원);
        assertEquals(2, olderMemberThanAvg.size());
        System.out.println("나이가 평균보다 많은 회원들 :: ");
        olderMemberThanAvg.forEach(n -> System.out.println(n));

        String 한건이라도주문한고객쿼리 = bq+"where (select count(o) from Order o where m = o.member) > 0";
        final List<Member> orderMember = subQueryService.getMemberWithParam(한건이라도주문한고객쿼리);
        assertEquals(8, memberRepository.count());
        assertEquals(4, orderMember.size());
        System.out.println("한 건이라도 주문한 고객들");
        orderMember.forEach(n-> System.out.println(n));

        // 조건식
        String EXIST_TEST=bq+"where exists (select t from m.team t where t.name ='팀A')";
        final List<Member> teamAMember = subQueryService.getMemberWithParam(EXIST_TEST);
        assertEquals(4, teamAMember.size());
        System.out.println("팀A인 고객들");
        teamAMember.forEach(n -> System.out.println(n));

        String In식 = bq + "where m.username in ('아라한사', '강백호')";
        final List<Member> inStatementMember = subQueryService.getMemberWithParam(In식);
        assertEquals(2, inStatementMember.size());

        String 컬렉션식 = bq+"where m.orders is empty";
        final List<Member> orderEmptyMember = commonService.getClassTypeWithParam(컬렉션식, Member.class);
        assertEquals(4, orderEmptyMember.size());
        System.out.println("주문이 없는 고객들");
        orderEmptyMember.forEach(n -> System.out.println(n));

        // case 식
        String case식="select case t.name when '팀A' then '인센티브110%' when '팀B' then '인센티브120%' else '인센티브105%' end from Team t";
        final List<String> caseTeams = commonService.getClassTypeWithParam(case식, String.class);
        System.out.println("case 식 처리한 팀 결과 ! ");
        caseTeams.forEach(n-> System.out.println(n));


    }




    @Autowired
    OrderRepository orderRepository;


    @Before
    public void pathExpressionTest() throws Exception{
        // save
        Team team1 = new Team("팀A");
        Team team2 = new Team("팀B");

        final Member member1 = new Member("아라한사", team1, 30);
        final Member member2 = new Member("임형주", team1, 20);
        final Member member3 = new Member("수지", team2, 10);
        final Member member4 = new Member("강백호", team2, 40);

        Product product = new Product("productA");
        Product product2 = new Product("productB");
        Product product3 = new Product("productC");

        Address address = new Address("JINJU", "NAMGU", "402743");
        Address address2 = new Address("INCHEON", "NAMGU", "402743");
        Address address3 = new Address("SEOUL", "NAMGU", "402743");

        Order order = new Order(member1, product, address);
        Order order2 = new Order(member2, product, address);
        Order order3 = new Order(member3, product2, address2);
        Order order4 = new Order(member4, product3, address3);

        List<Order> orders = Arrays.asList(order, order2, order3, order4);
        orderRepository.save(orders);
        assertEquals(4L, orderRepository.count());

        // save Member
        List<Member> members =  Arrays.asList(
                new Member("설현", team1, 25), new Member("소지섭", team2, 25),
                new Member("홍석천", team1, 25), new Member("채치수",team2, 25)
        );
        memberRepository.save(members);
    }



}
