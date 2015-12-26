package com.example.jpql;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.example.LearnJpqlApplication;
import com.example.domain.Member;
import com.example.repository.MemberRepository;
import com.example.service.jpql.Chap10TestService_JPQL;



@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes= LearnJpqlApplication.class)
@EnableTransactionManagement
public class JPQLTest {
	
	private static final String 아라한사 = "아라한사";
	private static final String KIM = "kim";
	
	@Autowired Chap10TestService_JPQL jpqlTestService;
	@Autowired MemberRepository memberRepository;
	
	@Test
	public void saveTest() throws Exception {
		// save => 3
		jpqlTestService.saveMember(new Member(아라한사, 25));
		jpqlTestService.saveMemberVoid(new Member("임형주", 10));
		jpqlTestService.saveMemberVoid(new Member(KIM, 23));
		assertEquals(3, memberRepository.findAll().size());
		assertEquals(3, jpqlTestService.getMembersWithTypeQuery().size());
		
		// search => kim
		System.out.println("JPQL Query ! ");
		assertEquals(KIM, jpqlTestService.getMembersWithJPQL().get(0).getUsername());
		System.out.println("Criteria Query ! ");
		assertEquals(KIM, jpqlTestService.getMembersWithCriteria().get(0).getUsername());
		
		// unclear query type
		List unclearType = jpqlTestService.getUnclearType();
		for (Object o : unclearType) {
			Object[] result = (Object[])o;
			System.out.println("username :: "+result[0]);
			System.out.println("age :: "+result[1]);
		}
		
		// parameter Binding, locationBinding
		List<Member> membersWithTypeQueryParameterBinding = jpqlTestService.getMembersWithTypeQueryParameterBinding(아라한사);
		assertEquals(아라한사, membersWithTypeQueryParameterBinding.get(0).getUsername());
		assertEquals(KIM, jpqlTestService.getMembersWithTypeQueryLocationBinding(KIM).get(0).getUsername());
		
		
	}

}
