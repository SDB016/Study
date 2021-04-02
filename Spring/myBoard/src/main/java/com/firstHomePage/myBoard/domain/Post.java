package com.firstHomePage.myBoard.domain;

import lombok.Getter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Table(name = "posts")
public class Post {

    @Id @GeneratedValue
    @Column(name="post_id")
    private Long id;

    @NotEmpty
    private String title;
    @NotEmpty
    private String contents;
    private LocalDateTime lastUpdateTime;
    private int views;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

    public Post(Member member, @NotEmpty String title, @NotEmpty String contents, LocalDateTime lastUpdateTime) {
        this.title = title;
        this.contents = contents;
        this.lastUpdateTime = lastUpdateTime;
        this.member = member;
    }
}
