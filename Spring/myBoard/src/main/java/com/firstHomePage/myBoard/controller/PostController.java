package com.firstHomePage.myBoard.controller;

import com.firstHomePage.myBoard.domain.Member;
import com.firstHomePage.myBoard.domain.Post;
import com.firstHomePage.myBoard.repository.PostRepository;
import com.firstHomePage.myBoard.service.MemberService;
import com.firstHomePage.myBoard.service.PostService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final PostRepository postRepository;

    @PostMapping("/post")
    public CreatePostResponse savePost(@RequestBody @Valid CreatePostRequest request){

        Post post = Post.createPost(request.title, request.contents);

        Long id = postService.save(post);
        return new CreatePostResponse(id);
        //멤버, 댓글 추가 필요
    }

    @GetMapping("/post")
    public ResultPost getAllPost(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "5") int size
    ){

        List<Post> posts = postService.findAll(page, size);
        List<PostDto> collect = posts.stream()
                .map(p -> new PostDto(p.getId(), p.getCreatedBy(), p.getTitle(), p.getContents(), p.getViews(), p.getLastModifiedDate()))
                .collect(toList());
        return new ResultPost<>(collect);
    }

    @GetMapping("/post/keyword")
    public ResultPost getAllPostByKeyword(
            @RequestBody @Valid GetPostByKeywordRequest request
    ) {
        List<Post> postList = postService.findAllByKeyword(request.getKeyword());
        List<PostDto> collect = postList.stream()
                .map(p -> new PostDto(p.getId(), p.getCreatedBy(), p.getTitle(), p.getContents(), p.getViews(), p.getLastModifiedDate()))
                .collect(toList());
        return new ResultPost(collect);
    }

    @GetMapping("/post/id")
    public ResultPost getAllPostByUserId(
            @RequestBody @Valid GetPostByIdRequest request
    ) {
        List<Post> postList = postService.findAllByUserId(request.getUserId());
        List<PostDto> collect = postList.stream()
                .map(p -> new PostDto(p.getId(), p.getCreatedBy(), p.getTitle(), p.getContents(), p.getViews(), p.getLastModifiedDate()))
                .collect(toList());
        return new ResultPost(collect);
    }

    @GetMapping("/post/{id}")
    public ResultPost getPost(@PathVariable Long id){

        Post post = postService.findOne(id);
        PostDto postDto = new PostDto(post.getId(), post.getCreatedBy(), post.getTitle(), post.getContents(), post.getViews(), post.getLastModifiedDate());
        return new ResultPost(postDto);
    }

    @PatchMapping("/post/{id}")
    public String updatePost(@PathVariable Long id,
                           @RequestBody @Valid UpdatePostRequest request){

        postService.update(id, request.getTitle(), request.getContents());
        return "Update Done!";
    }

    @DeleteMapping("/post/{id}")
    public String deletePost(@PathVariable Long id){

        postService.delete(id);
        return "Delete Done!";
    }


    //==static class==//
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
        private String writer;
        private String title;
        private String contents;
        //private List<Comment>comments;
        private int views;
        private LocalDateTime time;
    }

    @Data
    static class UpdatePostRequest{

        private String title;
        private String contents;
    }

    @Data
    @Getter
    static class GetPostByKeywordRequest{

        private String keyword;
    }

    @Data
    @Getter
    static class GetPostByIdRequest{

        private String userId;
    }
}
