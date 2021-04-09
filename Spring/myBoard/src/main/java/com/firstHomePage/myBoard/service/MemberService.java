package com.firstHomePage.myBoard.service;

import com.firstHomePage.myBoard.domain.Member;
import com.firstHomePage.myBoard.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public Long join(Member member){
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {

        List<Member> members = memberRepository.findByLoginInfo(member.getLoginId(), member.getLoginPwd());
        if(!members.isEmpty()){
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    /**
     * 전체 회원 조회
     */
    public List<Member> findAll(){
        return memberRepository.findAll();
    }

    public Member findOne(Long memberId){
        return memberRepository.findOne(memberId);
    }

    @Transactional
    public void delete(Member member) {
        memberRepository.delete(member);
    }

    @Transactional
    public void update(Long id, String newId, String newPwd, String newNickname) {
        Member member = memberRepository.findOne(id);

        String new_id = Optional.ofNullable(newId).orElse(member.getLoginId());
        String new_pwd = Optional.ofNullable(newPwd).orElse(member.getLoginPwd());
        String new_nickname = Optional.ofNullable(newNickname).orElse(member.getNickname());

        member.setLoginId(new_id);
        member.setLoginPwd(new_pwd);
        member.setNickname(new_nickname);

        memberRepository.save(member);
    }

    public Member findOneByName(String name) {
        return memberRepository.findOneByName(name);
    }
}
