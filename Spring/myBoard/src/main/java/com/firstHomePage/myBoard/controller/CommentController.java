package com.firstHomePage.myBoard.controller;

import com.firstHomePage.myBoard.domain.Comment;
import com.firstHomePage.myBoard.domain.Member;
import com.firstHomePage.myBoard.domain.Post;
import com.firstHomePage.myBoard.service.CommentService;
import com.firstHomePage.myBoard.service.MemberService;
import com.firstHomePage.myBoard.service.PostService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final PostService postService;
    private final MemberService memberService;

    @PostMapping("/post/{postId}/comment")
    public CreateCommentResponse createComment(@RequestBody @Valid CreateCommentRequest request, @PathVariable Long postId){

        Post post = postService.findOne(postId);
        Comment comment = Comment.createComment(post, request.getContents());
        Long commentId = commentService.save(comment);

        return new CreateCommentResponse(commentId);
    }

    @GetMapping("/post/{postId}/comment")
    public ResultComment getAllCommentByPostWithMember(
            @PathVariable Long postId,
            @RequestParam(value = "offset", defaultValue = "0") int offset,
            @RequestParam(value = "limit", defaultValue = "10") int limit)
    {

        List<Comment> comments = commentService.findAllByPostWithMember(postService.findOne(postId), offset, limit);
        List<CommentDto> collect = comments.stream()
                .map(comment -> new CommentDto(comment))
                .collect(toList());

        return new ResultComment(collect);
    }

    @GetMapping("/comment/member/{memberId}")
    public ResultComment getAllCommentByMember(
            @PathVariable(name = "memberId") Long memberId,
            @RequestParam(value = "offset", defaultValue = "0") int offset,
            @RequestParam(value = "limit", defaultValue = "10") int limit)
    {

        List<Comment> comments = commentService.findAllByMemberWithPost(memberService.findOne(memberId), offset, limit);
        List<CommentDto> collect = comments.stream()
                .map(comment -> new CommentDto(comment))
                .collect(toList());

        return new ResultComment(collect);
    }


    @PatchMapping("/post/{postId}/comment/{commentId}")
    public String updateComment(@PathVariable(name = "postId")Long postId,
                              @PathVariable(name = "commentId")Long commentId,
                              @RequestBody @Valid UpdateCommentRequest request){

        commentService.update(commentId, request.getContents());

        return "Update Done!";
    }

    @DeleteMapping("/post/{postId}/comment/{commentId}")
    public String deleteComment(@PathVariable(name = "postId")Long postId,
                              @PathVariable(name = "commentId")Long commentId){

        Comment comment = commentService.findOne(commentId);
        commentService.delete(comment);

        return "Delete Done!";
    }


    //==static class==//
    @Data
    @AllArgsConstructor
    static class ResultComment<T>{
        private T data;
    }

    @Data
    static class CreateCommentRequest{

        private String contents;
    }

    @Data
    @AllArgsConstructor
    static class CreateCommentResponse{

        private Long id;
    }

    @Data
    static class UpdateCommentRequest{

        private String contents;
    }

    @Getter
    static class CommentDto{
        private Long id;
        private String name;
        private String title;
        private String content;
        private LocalDateTime updateTime;

        public CommentDto(Comment comment) {
            id = comment.getId();
            name = comment.getCreatedBy();
            title = comment.getPost().getTitle();
            content = comment.getContents();
            updateTime = comment.getLastModifiedDate();
        }
    }
}
