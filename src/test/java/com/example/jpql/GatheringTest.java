package com.example.jpql;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
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
import com.example.domain.Team;
import com.example.repository.MemberRepository;
import com.example.service.jpql.Chap10TestService_JPQL;
import com.example.service.jpql.ProjectionService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes= LearnJpqlApplication.class)
@EnableTransactionManagement
public class GatheringTest {
	@Autowired Chap10TestService_JPQL jpqlTestService;
	@Autowired MemberRepository memberRepository;
	@Autowired ProjectionService projectionService;
	
	@Test
	public void gatheringTest() throws Exception {
		memberRepository.save(new Member(30));
		memberRepository.save(new Member(20));
		memberRepository.save(new Member(10));
		memberRepository.save(new Member(20));
		
		Object[] result = jpqlTestService.getGatheringInfo();
		
		System.out.println("count :: "+result[0]);
		System.out.println("sum :: "+result[1]);
		System.out.println("avg :: "+result[2]);
		System.out.println("max :: "+result[3]);
		System.out.println("min :: "+result[4]);
		assertEquals(4L, result[0]);
		assertEquals(80L, result[1]);
		assertEquals(20.0, result[2]);
		assertEquals(30, result[3]);
		assertEquals(10, result[4]);
	}
	
	@Test
	public void gatheringWithTeam() throws Exception {
		Team team1 = new Team("아라한사팀");
		Team team2 = new Team("임형주팀");
		Team team3 = new Team("아동팀");
				
		List<Member> memberList = Arrays.asList(
				 new Member(20, team1), new Member(30, team1), new Member(10, team1),
				 new Member(40, team2), new Member(50, team2), new Member(60, team2),
				 new Member(5, team3), new Member(4, team3)
				);
		memberRepository.save(memberList);
		
		
		List<Object[]> gatheringInfoWithGroupBy = jpqlTestService.getGatheringInfoWithGroupBy();
		for (Object[] result : gatheringInfoWithGroupBy) {
			System.out.println("TeamName :: "+result[0]);
			System.out.println("count :: "+result[1]);
			System.out.println("sum :: "+result[2]);
			System.out.println("avg :: "+result[3]);
			System.out.println("max :: "+result[4]);
			System.out.println("min :: "+result[5]);
		}
		
		
	}
	
}
