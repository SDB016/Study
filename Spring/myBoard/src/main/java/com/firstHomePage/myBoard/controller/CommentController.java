package com.firstHomePage.myBoard.controller;

import com.firstHomePage.myBoard.domain.Comment;
import com.firstHomePage.myBoard.domain.Member;
import com.firstHomePage.myBoard.domain.Post;
import com.firstHomePage.myBoard.service.CommentService;
import com.firstHomePage.myBoard.service.MemberService;
import com.firstHomePage.myBoard.service.PostService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final PostService postService;
    private final MemberService memberService;

    @PostMapping("/post/{id}/comment")
    public CreateCommentResponse createComment(@RequestBody @Valid CreateCommentRequest request, @PathVariable Long postId){

        //=========수정 필요===========//
        Member member = memberService.findOne(1L);
        //============================//

        Post post = postService.findOne(postId);
        Comment comment = Comment.createComment(member, post, request.getContent());
        Long commentId = commentService.save(comment);

        return new CreateCommentResponse(commentId);
    }

    public void getAllCommentByPost(){

    }

    public void getAllCommentByMember(){

    }


    public void updateComment(){

    }

    public void deleteComment(){

    }


    //==static class==//
    @Data
    @AllArgsConstructor
    static class ResultComment<T>{
        private T data;
    }

    @Data
    static class CreateCommentRequest{

        private String content;
    }

    @Data
    @AllArgsConstructor
    static class CreateCommentResponse{

        private Long id;
    }
}
