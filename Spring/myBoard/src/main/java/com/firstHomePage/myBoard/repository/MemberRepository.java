package com.firstHomePage.myBoard.repository;

import com.firstHomePage.myBoard.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

    private final EntityManager em;

    public void save(Member member){
        em.persist(member);
    }

    public Member findOne(Long memberId){
        return em.find(Member.class, memberId);
    }

    public List<Member> findAll(){
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }


    public List<Member> findByName(String name){
        return em.createQuery("select m from Member m where m.name =:name",
                Member.class)
                .setParameter("name", name)
                .getResultList();
    }


    public List<Member> findByMemberInfo(String name, String nickname, int age){
        return em.createQuery("select m from Member m " +
                "where m.name =:name " +
                "and m.nickname =:nickname " +
                "and m.age =:age", Member.class)
                .setParameter("name", name)
                .setParameter("nickname", nickname)
                .setParameter("age", age)
                .getResultList();
    }

    public List<Member> findByLoginInfo(String loginId, String loginPwd){
        return em.createQuery("select m from Member m " +
                "where m.loginId =:loginId " +
                "and m.loginPwd =:loginPwd", Member.class)
                .setParameter("loginId", loginId)
                .setParameter("loginPwd", loginPwd)
                .getResultList();
    }
}
