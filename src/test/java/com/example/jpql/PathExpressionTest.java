package com.example.jpql;

import com.example.domain.*;
import com.example.repository.OrderRepository;
import com.example.service.jpql.PathExpressionService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;
import static org.junit.Assert.*;

/**
 * Created by jarvis on 15. 12. 30..
 */
public class PathExpressionTest  extends TestJPQLConfig{

    @Autowired
    PathExpressionService pathExpressionService;
    @Autowired
    OrderRepository orderRepository;

    @Test
    public void pathExpressionTest() throws Exception{
        Team team1 = new Team("팀A");
        Team team2 = new Team("팀B");

        final Member member1 = new Member("아라한사", team1, 30);
        final Member member2 = new Member("임형주", team1, 20);
        final Member member3 = new Member("수지", team2);
        final Member member4 = new Member("강백호", team2);

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

        System.out.println("경로 표현식 조회 ");
        final List<Team> teamFromPathExpression = pathExpressionService.getTeamFromPathExpression();
        System.out.println(" team : " + teamFromPathExpression);
        assertEquals(2, teamFromPathExpression.size());
    }

}
