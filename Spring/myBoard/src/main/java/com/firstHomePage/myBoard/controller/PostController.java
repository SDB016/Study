package com.firstHomePage.myBoard.controller;

import com.firstHomePage.myBoard.domain.Member;
import com.firstHomePage.myBoard.domain.Post;
import com.firstHomePage.myBoard.service.MemberService;
import com.firstHomePage.myBoard.service.PostService;
import com.firstHomePage.myBoard.web.PostForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class PostController {

    private final MemberService memberService;
    private final PostService postService;

    @GetMapping("/posts/create")
    public String createForm(Model model) {

        List<Member> members = memberService.findAll();

        model.addAttribute("members", members);
        model.addAttribute("postForm", new PostForm());
        return "posts/createPostForm";
    }

    @PostMapping("/posts/create")
    public String create(@RequestParam("memberId") Long memberId, @Valid PostForm form, BindingResult result) {
        if (result.hasErrors()) {
            return "posts/createPostForm";
        }
        Member member = memberService.findOne(memberId);
        Post post = Post.createPost(member,form.getTitle(), form.getContents());

        postService.save(post);

        return "redirect:/";
    }

    @GetMapping("/posts")
    public String postList(Model model){

        List<Post> posts = postService.findAll();

        model.addAttribute("posts", posts);
        return "posts/postList";
    }

    @GetMapping("/posts/{postId}")
    public String post(@PathVariable("postId") Long postId, Model model) {

        Post post = postService.findOne(postId);
        model.addAttribute("findPost", post);

        return "posts/postOne";
    }
}
