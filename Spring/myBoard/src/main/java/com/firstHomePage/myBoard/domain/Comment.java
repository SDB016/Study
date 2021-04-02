package com.firstHomePage.myBoard.domain;

import lombok.Getter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
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

    public Comment(Member member, Post post,@NotEmpty String contents, LocalDateTime lastUpdateTime) {
        this.contents = contents;
        this.lastUpdateTime = lastUpdateTime;
        this.post = post;
        this.member = member;
    }
}
