package com.example.querydsl;

import com.example.domain.Product;
import com.example.domain.QProduct;
import com.example.jpql.TestJPQLConfig;
import com.example.repository.ProductRepository;
import com.mysema.query.jpa.impl.JPAQuery;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

import static org.junit.Assert.*;
/**
 * Created by arahansa on 2016-01-05.
 */
public class SearchConditionQuery02 extends TestJPQLConfig {


    @PersistenceContext
    EntityManager em;

    @Autowired
    ProductRepository productRepository;

    @Before
    public void setup(){
        Random random = new Random();

        List<Product> products = new ArrayList<>();

        for(int i=0;i<50;i++){
            products.add(new Product("나쁜상품",random.nextInt(300000), random.nextInt(50)));
            products.add(new Product("좋은상품",random.nextInt(300000), random.nextInt(50)));
        }

        productRepository.save(products);
    }

    @Test
    public void searchConditionTest() throws Exception{
        JPAQuery query = new JPAQuery(em);
        QProduct product = QProduct.product;
        List<Product> list = query.from(product).where(product.name.eq("좋은상품").and(product.price.gt(20000))).list(product);
        list.forEach(n-> {
            System.out.println(n);
            assertEquals("좋은상품", n.getName());
            assertTrue(n.getPrice()>20000);
        });
    }
    
    @Test
    public void pagingTest() throws Exception{
        JPAQuery query = new JPAQuery(em);
        QProduct product = QProduct.product;

        final List<Product> list = query.from(product).where(product.price.gt(20))
                .orderBy(product.price.desc(), product.stockAmount.asc())
                .offset(10).limit(20)
                .list(product);
        // 실제로는 listResults(item); 을 출력해야 count를 한번 더 호출한다.

        System.out.println("좋은 상품들 출력 ");
        list.forEach(n-> System.out.println(n));

    }
}
