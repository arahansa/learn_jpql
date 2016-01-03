package com.example.criteria;

import com.example.domain.Member;
import com.example.domain.Product;
import com.example.domain.Team;
import com.example.jpql.JPQLTest;
import com.example.jpql.TestJPQLConfig;
import com.example.repository.MemberRepository;
import com.example.service.criteria.CriteriaBasicService;
import com.example.service.criteria.CriteriaGroupHavingService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by jarvis on 16. 1. 3..
 */
public class CriteriaGroupHavingTest extends TestJPQLConfig {

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    CriteriaGroupHavingService criteriaGroupHavingService;


    @Before
    public void setup(){
        // save
        Team team1 = new Team("팀A");
        Team team2 = new Team("팀B");

        List<Member> members = Arrays.asList(
                new Member("아라한사", team1, 30), new Member("임형주", team1, 20),
                new Member("류형석", team1, 15), new Member("캣츠컬", team1, 12),
                new Member("수지", team2, 40), new Member("강백호", team2, 15),
                new Member("복덩어리", team2, 25), new Member("방패연", team2, 1)
        );
        memberRepository.save(members);
    }

    @Test
    public void criteriaGroupByTest() throws Exception{
        System.out.println("팀이름과 가장 나이 많은 사람과 가장 나이 적은 사람 :: ");
        final List<Object[]> membersWithOldestAndYoungest = criteriaGroupHavingService.getMembersWithOldestAndYoungest();
        showTeamnameMaxMinAge(membersWithOldestAndYoungest);

        System.out.println("팀에서 최소 나이가 10 살 이상인 팀만 해당되게... :: ");
        final List<Object[]> youngestOlderThan10InTeam = criteriaGroupHavingService.getMembersYoungestOlderThan10InTeam();
        showTeamnameMaxMinAge(youngestOlderThan10InTeam);
    }

    private void showTeamnameMaxMinAge(List<Object[]> objects){
        for (Object[] row : objects) {
            String name = (String) row[0];
            int maxAge = (int) row[1];
            int minAge = (int) row[2];
            System.out.println("팀 : "+name+" 의 가장 많은 나이 : "+maxAge+" , 가장 적은 나이 : "+minAge);
        }
    }

}
