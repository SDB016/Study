package com.firstHomePage.myBoard.service;

import com.firstHomePage.myBoard.domain.Post;
import com.firstHomePage.myBoard.repository.PostRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@Transactional
@SpringBootTest
public class PostServiceTest {

    @Autowired private PostService postService;
    @Autowired private PostRepository postRepository;

    @Test
    public void 저장() throws Exception {
        //given
        Post post = new Post();
        post.setTitle("첫 글");

        //when
        Long saveId = postService.save(post);

        //then
        assertEquals(post, postRepository.findOne(saveId));
    }

    @Test
    public void 목록_조회() throws Exception {
        //given
        Post post1 = new Post();
        Post post2 = new Post();
        post1.setTitle("첫 글");
        post2.setTitle("두번째 글");

        //when
        postService.save(post1);
        postService.save(post2);

        List<Post> all = postRepository.findAll(0,10);
        //then
        assertEquals(2, all.size());
    }
}