package com.example.querydsl;

import com.example.domain.*;
import com.example.jpql.TestJPQLConfig;
import com.example.repository.OrderRepository;
import com.mysema.query.jpa.impl.JPAQuery;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sound.midi.Soundbank;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by arahansa on 2016-01-05.
 */
public class JoinTest03 extends TestJPQLConfig {

    @Autowired
    OrderRepository orderRepository;
    @PersistenceContext
    EntityManager em;

    JPAQuery query ;

    QOrder order = QOrder.order;
    QMember member = QMember.member;
    QProduct product = QProduct.product;

    @Before
    public void setup(){
        query = new JPAQuery(em);
        // save
        Team team1 = new Team("팀A");
        Team team2 = new Team("팀B");

        final Member member1 = new Member("아라한사", team1, 30);
        final Member member2 = new Member("임형주", team1, 20);
        final Member member3 = new Member("수지", team2, 10);
        final Member member4 = new Member("강백호", team2, 40);

        Product product = new Product("productA", 10, 3);
        Product product2 = new Product("productB", 50, 10);
        Product product3 = new Product("productC", 20, 5);

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
    }

    @Test
    public void joinTest() throws Exception{
        System.out.println("상품 주문한 것들 :: ");
        List<Order> list = query.from(order).join(order.member, member).leftJoin(order.product, product).list(order);
        list.forEach(n-> System.out.println(n));
    }
    
    @Test
    public void onStatement() throws Exception{
        System.out.println(" On 구문 : product의 stockAmount 가 4이상인 것 이상뽑기 ");
        final List<Order> list = query.from(order).leftJoin(order.product, product).on(product.stockAmount.gt(4)).list(order);
        list.forEach(n-> {
            System.out.println(n);
            assertTrue(list.get(0).getProduct().getStockAmount() > 4);
        });
    }

}
