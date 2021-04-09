package com.firstHomePage.myBoard.service;

import com.firstHomePage.myBoard.domain.Comment;
import com.firstHomePage.myBoard.domain.Member;
import com.firstHomePage.myBoard.domain.Post;
import com.firstHomePage.myBoard.repository.CommentRepository;
import com.firstHomePage.myBoard.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    @Transactional
    public Long save(Comment comment) {
        commentRepository.save(comment);
        return comment.getId();
    }

    public Comment findOne(Long id) {
        return commentRepository.findOne(id);
    }

    public List<Comment> findAll(){
        return commentRepository.findAll();
    }

    public List<Comment> findAllByPost(Post post) {
        return commentRepository.findAllByPost(post);
    }

    @Transactional
    public void update(Long commentId, String contents) {
        Comment comment = commentRepository.findOne(commentId);
        comment.setContents(contents);
        comment.setLastUpdateTime(LocalDateTime.now());
        commentRepository.save(comment);
    }

    @Transactional
    public void delete(Comment comment) {
        commentRepository.delete(comment);
    }

    public List<Comment> findAllByPostWithMember(Post post, int offset, int limit) {

        return commentRepository.findAllByPostWithMember(post, offset, limit);
    }

    public List<Comment> findAllByMemberWithPost(Member member, int offset, int limit) {
        return commentRepository.findAllByMemberWithPost(member, offset, limit);
    }
}
