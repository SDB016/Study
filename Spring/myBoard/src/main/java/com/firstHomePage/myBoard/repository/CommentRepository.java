package com.firstHomePage.myBoard.repository;

import com.firstHomePage.myBoard.domain.Comment;
import com.firstHomePage.myBoard.domain.Member;
import com.firstHomePage.myBoard.domain.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CommentRepository {

    private final EntityManager em;

    public void save(Comment comment) {
        em.persist(comment);
    }

    public Comment findOne(Long commentId) {
        return em.find(Comment.class, commentId);
    }

    public List<Comment> findAll(){
        return em.createQuery("select c from Comment c", Comment.class)
                .getResultList();
    }

    public List<Comment> findAllByPost(Post post){
        return em.createQuery("select c from Comment c where c.post =: post", Comment.class)
                .setParameter("post", post)
                .getResultList();
    }

    public void delete(Comment comment) {
        em.remove(comment);
    }

    public List<Comment> findAllByPostWithMember(Post post, int offset, int limit) {

        return em.createQuery(
                "select c from Comment c" +
                        " join fetch c.member m" +
                        " where c.post =: post", Comment.class)
                .setParameter("post", post)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }

    public List<Comment> findAllByMemberWithPost(Member member, int offset, int limit) {

        return em.createQuery(
                "select c from Comment c" +
                        " join fetch c.post p" +
                        " where c.member =: member", Comment.class)
                .setParameter("member", member)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }
}
