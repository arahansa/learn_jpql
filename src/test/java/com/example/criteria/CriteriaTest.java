package com.example.criteria;

import com.example.domain.Member;
import com.example.jpql.TestJPQLConfig;
import com.example.repository.MemberRepository;
import com.example.service.criteria.CriteriaBasicService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.Tuple;
import java.util.Arrays;
import java.util.List;
import static org.junit.Assert.*;
/**
 * Created by arahansa on 2016-01-03.
 */
public class CriteriaTest extends TestJPQLConfig {

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    CriteriaBasicService criteriaBasicService;

    @Before
    public void setup() throws Exception{
        final Member member1 = new Member("아라한사", 30);
        final Member member2 = new Member("임형주",  20);
        final Member member3 = new Member("수지",  10);
        final Member member4 = new Member("강백호", 40);
        final List<Member> members = Arrays.asList(member1, member2, member3, member4);
        memberRepository.save(members);
    }

    @Test
    public void memberTest() throws Exception{
        List<Member> members = criteriaBasicService.getMembers();
        assertEquals(4, members.size());

        System.out.println(" === 유저네임 아라한사로 불러오기");
        members = criteriaBasicService.getMembersWithUsernameCondition("아라한사");
        assertEquals( 30 , members.get(0).getAge());
        members.forEach(n-> System.out.println(n));

        System.out.println(" === 10살보다 많은 사람 ");
        members = criteriaBasicService.getMemberOlderthan10();
        members.forEach(n->assertTrue(n.getAge() > 10));
        members.forEach(n-> System.out.println(n));

        System.out.println(" === many select 로 불러온 유저들");
        members = criteriaBasicService.getMembersWithManySelect();
        members.forEach(n-> System.out.println(n));
        members.forEach(n-> System.out.println(n));
    }
    
    @Test
    public void distinctTest() throws Exception{
        final Member member1 = new Member("아라한사", 30);
        memberRepository.save(member1);
        List<Member> members = criteriaBasicService.getMembers();
        assertEquals(5, members.size());
    
        members = criteriaBasicService.getMembersDistinct();
        assertEquals(4, members.size());
    }
    
    @Test
    public void tupleTest() throws Exception{
        final List<Tuple> memberWithTuple = criteriaBasicService.getMemberWithTuple();
        for (Tuple tuple : memberWithTuple) {
            String username = tuple.get("username", String.class);
            Integer age = tuple.get("age", Integer.class);
            System.out.println("유저네임 : "+username + " , age :: "+age);
        }
    }

}
