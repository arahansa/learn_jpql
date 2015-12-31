package com.example.jpql;

import com.example.LearnJpqlApplication;
import com.example.repository.MemberRepository;
import com.example.service.jpql.Chap10TestService_JPQL;
import com.example.service.jpql.CommonService;
import com.example.service.jpql.ProjectionService;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;
/**
 * Created by jarvis on 15. 12. 27..
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes= LearnJpqlApplication.class)
@EnableTransactionManagement
@Transactional
public abstract class TestJPQLConfig {

    @Autowired
    Chap10TestService_JPQL jpqlTestService;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    ProjectionService projectionService;

    @Autowired
    CommonService commonService;

}
