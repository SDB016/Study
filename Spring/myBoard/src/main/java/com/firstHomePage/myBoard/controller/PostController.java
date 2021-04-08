package com.firstHomePage.myBoard.controller;

import com.firstHomePage.myBoard.domain.Comment;
import com.firstHomePage.myBoard.domain.Member;
import com.firstHomePage.myBoard.domain.Post;
import com.firstHomePage.myBoard.repository.PostRepository;
import com.firstHomePage.myBoard.service.MemberService;
import com.firstHomePage.myBoard.service.PostService;
import com.firstHomePage.myBoard.web.PostForm;
import com.firstHomePage.myBoard.web.commentForm;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final MemberService memberService;

    @PostMapping("/post")
    public CreatePostResponse savePost(@RequestBody @Valid CreatePostRequest request){

        //====수정 필요=====//
        Member member = memberService.findOne(1L);
        //============================//

        Post post = Post.createPost(member, request.title, request.contents);

        Long id = postService.save(post);
        return new CreatePostResponse(id);
        //멤버, 댓글 추가 필요요
    }

    @GetMapping("/post")
    public ResultPost getAllPost(){

        List<Post> posts = postService.findAll();
        List<PostDto> collect = posts.stream()
                .map(p -> new PostDto(p.getId(), p.getTitle(), p.getContents(), p.getViews(), p.getLastUpdateTime()))
                .collect(toList());
        return new ResultPost<>(collect);
    }

    @GetMapping("/post/{id}")
    public ResultPost getPost(@PathVariable Long id){

        Post post = postService.findOne(id);
        PostDto postDto = new PostDto(post.getId(), post.getTitle(), post.getContents(), post.getViews(), post.getLastUpdateTime());
        return new ResultPost(postDto);
    }

    @PatchMapping("/post/{id}")
    public String updatePost(@PathVariable Long id,
                           @RequestBody @Valid UpdatePostRequest request){

        postService.update(id, request.getTitle(), request.getContents());
        return "update Done!";
    }

    @DeleteMapping("/post/{id}")
    public String deletePost(@PathVariable Long id){

        postService.delete(id);
        return "delete Done!";
    }

    @Data
    @AllArgsConstructor
    static class ResultPost<T>{
        private T data;
    }

    @Data
    static class CreatePostRequest{

        private String title;
        private String contents;
    }

    @Data
    @AllArgsConstructor
    static class CreatePostResponse{

        private Long id;
    }

    @Data
    @AllArgsConstructor
    static class PostDto{

        private Long id;
        private String title;
        private String contents;
        //private Member writer;
        //private List<Comment>comments;
        private int views;
        private LocalDateTime time;
    }

    @Data
    @AllArgsConstructor
    static class UpdatePostRequest{

        private String title;
        private String contents;
    }
}
