package com.firstHomePage.myBoard.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter @Setter
@NoArgsConstructor
public class Comment {

    @Id @GeneratedValue
    @Column(name="comment_id")
    private Long id;

    @NotEmpty
    private String contents;
    private LocalDateTime lastUpdateTime;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;


    //==연관관계 메서드==//
    public void conPost(Post post) {
        this.post = post;
        post.getComments().add(this);
    }

    //==생성 메서드==//
    public static Comment createComment(Member member, Post post, String contents) {
        Comment comment = new Comment();
        comment.setMember(member);
        comment.conPost(post);
        comment.setContents(contents);
        comment.setLastUpdateTime(LocalDateTime.now());
        return comment;
    }
}
