package com.firstHomePage.myBoard.service;

import com.firstHomePage.myBoard.domain.Comment;
import com.firstHomePage.myBoard.domain.Member;
import com.firstHomePage.myBoard.domain.Post;
import com.firstHomePage.myBoard.repository.CommentRepository;
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
public class CommentServiceTest {

    @Autowired private CommentService commentService;
    @Autowired private CommentRepository commentRepository;
    @Autowired private MemberService memberService;
    @Autowired private PostService postService;

    @Test
    public void 저장() throws Exception {
        //given
        Post post = Post.createPost("첫 제목", "본문 내용입니다.");
        Comment comment = Comment.createComment(post, "첫 댓글입니다.");

        //when
        Long saveId = commentService.save(comment);

        //then
        assertEquals(comment, commentRepository.findOne(saveId));
        assertEquals(post.getContents(), commentRepository.findOne(saveId).getPost().getContents());
    }

    @Test
    public void 게시글별_조회() throws Exception {
        //given

        Post post1 = Post.createPost("첫 제목", "본문 내용입니다.");
        Post post2 = Post.createPost("두번째 제목", "두번쨰 본문 내용입니다.");
        postService.save(post1);
        postService.save(post2);

        Comment comment1 = Comment.createComment(post1, "첫 댓글입니다.");
        Comment comment2 = Comment.createComment(post1, "두번째 댓글입니다.");
        Comment comment3 = Comment.createComment(post2, "세번째 댓글입니다.");

        //when
        commentService.save(comment1);
        commentService.save(comment2);
        commentService.save(comment3);

        //then
        assertEquals(2, commentRepository.findAllByPost(post1).size());
    }


}