package com.example.demo;

import com.example.demo.aop.TimeTraceAop;
import com.example.demo.repository.MemberRepository;
import com.example.demo.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class springConfig {

    private final MemberRepository memberRepository;

    @Autowired
    public springConfig(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    /*
        private final DataSource dataSource;

        public springConfig(DataSource dataSource) {
            this.dataSource = dataSource;
        }*/
    /*
    private EntityManager em;

    @Autowired
    public springConfig(EntityManager em) {
        this.em = em;
    }
*/
    @Bean
    public MemberService memberService() {
        return new MemberService(memberRepository);
    }

    //@Bean
    //public MemberRepository memberRepository() {
        //return new MemoryMemberRepository();
        //return new JdbcMemberRepository(dataSource);
        //return new JdbcTemplateMemberRepository(dataSource);
        //return new JpaMemberRepository(em);
    //}
    @Bean
    public TimeTraceAop timeTraceAop(){
        return new TimeTraceAop();
    }
}
