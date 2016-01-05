package com.example.querydsl;

import com.example.domain.*;
import com.example.domain.dto.ProductDTO;
import com.example.jpql.TestJPQLConfig;
import com.example.repository.OrderRepository;
import com.mysema.query.Tuple;
import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.jpa.impl.JPAUpdateClause;
import com.mysema.query.types.Projections;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by arahansa on 2016-01-05.
 */
public class ProjectionResultReturn04 extends TestJPQLConfig {

    @Test
    public void 프로젝션테스트() throws Exception{
        QMember member = QMember.member;
        List<String> result = query.from(member).list(member.username);
        System.out.println("===  멤버들 이름 프로젝션 === ");
        result.forEach(n-> System.out.println(n));
    }
    
    @Test
    public void 튜플테스트() throws Exception{
        final List<Tuple> result = query.from(product).list(product.name, product.price);
        System.out.println("=== 상품 정보 === ");
        result.forEach(n->{
            System.out.println("상품 이름 :"+ n.get(product.name));
            System.out.println("상품 가격 : "+n.get(product.price));
        });
    }
    
    @Test
    public void 빈생성() throws Exception{
        final List<ProductDTO> products = query.from(product).list(Projections.bean(ProductDTO.class, product.name.as("username"), product.price));
        System.out.println(" === 프로덕트 DTO 생성된 것들 ===");
        products.forEach(n-> System.out.println(n));
    }
    
    @Test
    public void 수정배치쿼리() throws Exception{
        JPAUpdateClause updateClause = new JPAUpdateClause(em, product);
        long count = updateClause.where(product.name.like("product%")).set(product.price, product.price.add(1000)).execute();
        System.out.println(" 수정된 갯수 : "+count);
        
        final List<Product> products = query.from(product).list(product);
        products.forEach(n-> System.out.println(n));

    }


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
}
