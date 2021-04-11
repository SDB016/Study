package com.firstHomePage.myBoard.service;

import com.firstHomePage.myBoard.domain.Member;
import com.firstHomePage.myBoard.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Long join(Member member){
        if(isDuplicateMember(member.getLoginId(), member.getLoginPwd())){
            throw new IllegalStateException("이미 존재하는 ID & PASSWORD 입니다.");
        }
        //**password hashing**//
        String encodedPwd = passwordEncoder.encode(member.getLoginPwd());
        member.setLoginPwd(encodedPwd);
        //****//
        memberRepository.save(member);
        return member.getId();
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

    public String findIdByName(String name) {
        return memberRepository.findIdByName(name);
    }

    public String findPwdByNameId(String name, String id) {
        return memberRepository.findPwdByNameId(name, id);
    }

    public boolean login(String userId, String userPwd) {
        return isDuplicateMember(userId, userPwd);
    }


    private boolean isDuplicateMember(String userId, String userPwd) {

        List<Member> members = memberRepository.findByUserId(userId);
        List<String> pwds = members.stream()
                .map(m -> m.getLoginPwd())
                .collect(toList());

        for (String pwd : pwds) {
            if (passwordEncoder.matches(userPwd, pwd)){
                return true;
            }
        }
        return false;
    }
}
