package com.firstHomePage.myBoard.service;

import com.firstHomePage.myBoard.domain.Member;
import com.firstHomePage.myBoard.repository.MemberRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class MemberServiceTest {

    @Autowired private MemberService memberService;
    @Autowired private MemberRepository memberRepository;

    @Test
    public void 회원가입() throws Exception {
        //given
        Member member = new Member("sdb016","016","신동빈", "nick",25);

        //when
        Long memberId = memberService.join(member);

        //then
        assertEquals(member, memberRepository.findOne(memberId));
    }

    @Test(expected = IllegalStateException.class)
    public void 중복회원_예외() throws Exception {

        //given
        Member member1 = new Member("sdb016","016","신동빈", "nick",25);
        Member member2 = new Member("sdb016","016","홍길동", "hong",22);

        //when
        memberService.join(member1);
        memberService.join(member2);

        //then
        fail("예외가 발생해야 합니다.");
    }


}