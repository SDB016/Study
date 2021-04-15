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

    public void delete(Member member) {
        em.remove(member);
    }

    public String findIdByName(String name) {
        return em.createQuery(
                "select m.id from Member m where m.name =:name", String.class)
                .setParameter("name", name)
                .getResultList()
                .get(0);
    }

    public String findPwdByNameId(String name, String id) {
        return em.createQuery(
                "select m.loginPwd from Member m where m.name =:name and m.loginId =: id", String.class)
                .setParameter("name", name)
                .setParameter("id", id)
                .getResultList()
                .get(0);
    }

    public List<Member> findByUserId(String userId) {
        return em.createQuery(
                "select m from Member m where m.loginId =:userId", Member.class)
                .setParameter("userId", userId)
                .getResultList();
    }
}
