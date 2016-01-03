package com.example.jpql;

import com.example.domain.Member;
import com.example.service.jpql.NamedQueryService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import static org.junit.Assert.*;
/**
 * Created by arahansa on 2016-01-03.
 */
@Transactional
public class NamedQueryTest extends TestJPQLConfig  {

    @Autowired
    NamedQueryService namedQueryService;

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
    public void namedQueryTest() throws Exception{
        final List<Member> members = namedQueryService.getMembersByUserName("아라한사");
        assertEquals(30, members.get(0).getAge());
        assertEquals(4L, namedQueryService.getMemberCount());
    }
}
