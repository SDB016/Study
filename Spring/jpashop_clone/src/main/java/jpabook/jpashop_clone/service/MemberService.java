package jpabook.jpashop_clone.service;

import jpabook.jpashop_clone.domain.Member;
import jpabook.jpashop_clone.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor //생성자 인젝션 (final 변수만)
public class MemberService {

    private final MemberRepository memberRepository;

    /**
     *회원가입
     */
    @Transactional
    public Long join(Member member) {
        validateDuplicatedMember(member);
        return memberRepository.save(member);
    }

    private void validateDuplicatedMember(Member member) {

        List<Member> members = memberRepository.findMemberByName(member.getName());
        if (members.size() != 0){
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    /**
     *회원 전체 조회
     */
    public List<Member> findAll(){
        return memberRepository.findAll();
    }

    public Member findOne(Long memberId){
        return memberRepository.findOne(memberId);
    }
}
