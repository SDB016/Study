package com.firstHomePage.myBoard.service;

import com.firstHomePage.myBoard.domain.Comment;
import com.firstHomePage.myBoard.domain.Post;
import com.firstHomePage.myBoard.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
