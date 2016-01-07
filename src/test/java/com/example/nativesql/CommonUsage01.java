package com.example.nativesql;

import com.example.domain.*;
import com.example.jpql.TestJPQLConfig;
import com.example.repository.OrderRepository;
import com.mysema.query.jpa.impl.JPAQuery;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by arahansa on 2016-01-08.
 */
public class CommonUsage01 extends TestJPQLConfig{

    @PersistenceContext
    EntityManager em;


    @Test
    public void 엔티티조회() throws Exception{
        String sql ="SELECT ID, AGE, NAME, TEAM_ID FROM MEMBER WHERE AGE > ?";
        Query nativeQuery = em.createNativeQuery(sql, Member.class).setParameter(1, 20);
        final List<Member> resultList = nativeQuery.getResultList();
        assertEquals(2, resultList.size());
    }

    @Test
    public void 값조회() throws Exception{
        String sql ="SELECT ID, AGE, NAME, TEAM_ID FROM MEMBER WHERE AGE > ?";
        Query nativeQuery = em.createNativeQuery(sql).setParameter(1, 20);

        final List<Object[]> resultList = nativeQuery.getResultList();
        resultList.forEach(n->{
            System.out.println("id = "+n[0]);
            System.out.println("age = "+n[1]);
            System.out.println("name = "+n[2]);
            System.out.println("team_id = "+n[3]);
        });
        assertEquals(2, resultList.size());
    }
    
    @Test
    public void 결과매핑사용() throws Exception{
        String sql =
                "SELECT M.ID, AGE, NAME, TEAM_ID, I.ORDER_COUNT "+
                "FROM MEMBER M " +
                "LEFT JOIN " +
                        "(SELECT IM.ID, COUNT(*) AS ORDER_COUNT " +
                        "FROM ORDERS O, MEMBER IM WHERE O.MEMBER_ID = IM.ID GROUP BY IM.ID ) I " +
                "ON M.ID = I.ID";

        final Query nativeQuery = em.createNativeQuery(sql, "memberWithOrderCount");
        final List<Object[]> resultList = nativeQuery.getResultList();
        for(Object[] row : resultList){
            Member member = (Member) row[0];
            BigInteger orderCount = (BigInteger) row[1];

            System.out.println("Member = "+member);
            System.out.println("orderCount =" + orderCount);
        }

    }




    @Autowired
    OrderRepository orderRepository;

    @Before
    public void setup(){
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

}
