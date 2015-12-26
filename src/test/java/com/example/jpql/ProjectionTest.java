package com.example.jpql;

import static org.junit.Assert.*;

import java.util.List;

import javax.persistence.Embeddable;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.example.LearnJpqlApplication;
import com.example.domain.Member;
import com.example.domain.Order;
import com.example.domain.Product;
import com.example.domain.Team;
import com.example.domain.dto.UserDTO;
import com.example.repository.MemberRepository;
import com.example.service.jpql.Chap10TestService_JPQL;
import com.example.service.jpql.ProjectionService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes= LearnJpqlApplication.class)
@EnableTransactionManagement
public class ProjectionTest {
	
	private static final String KIM = "kim";
	private static final String 아라한사_팀 = "아라한사 팀";
	@Autowired Chap10TestService_JPQL jpqlTestService;
	@Autowired MemberRepository memberRepository;
	@Autowired ProjectionService projectionService;
	
	@Test
	public void projectionService() throws Exception {
		// setup
		Team team = new Team();
		team.setName(아라한사_팀);
		Member member = new Member(KIM, 20);
		member.setTeam(team);
		memberRepository.save(member);
		
		// projection Team
		System.out.println("프로젝션으로 값 얻기 ");
		List<Member> membersWithJPQLProjection = projectionService.getMembersWithJPQLProjection();
		membersWithJPQLProjection.forEach(n->System.out.println(n));
		assertEquals(20, membersWithJPQLProjection.get(0).getAge());
		List<Team> teams = projectionService.getTeamsWithJPQLProjection();
		assertEquals(아라한사_팀, teams.get(0).getName());
		
		// new keyword
		System.out.println("new 키워드로 값 얻기");
		List<UserDTO> entitiesWithNewKeyword = projectionService.getProjectionEntitiesWithNewKeyword();
		assertEquals(20, entitiesWithNewKeyword.get(0).getAge());
		assertEquals(KIM, entitiesWithNewKeyword.get(0).getUsername());
		
	}
	
	@Test
	public void saveOrderTest() throws Exception {
		Member member = new Member(KIM, 20);
		Product product = new Product();
		product.setName("맥북");
		Order order = new Order();
		order.setMember(member);
		order.setProduct(product);
		order.setOrderAmount(50);
		jpqlTestService.saveObject(order);
		
		List<Object[]> resultList = projectionService.getProjectionEntities();
		for (Object[] row : resultList) {
			Member getMember = (Member) row[0];
			Product getProduct = (Product) row[1];
			int orderAmount = (Integer) row[2];
			System.out.println("받은 멤버 :: "+getMember);
			System.out.println("받은 프로덕트 :: "+getProduct);
			System.out.println("양 :: "+orderAmount);
			assertEquals(20, getMember.getAge());
			assertEquals("맥북", getProduct.getName());
			assertEquals(50, orderAmount);
		}
	}

}
