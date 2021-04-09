package com.firstHomePage.myBoard.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
@SequenceGenerator(
        name = "posts_seq_generator",
        sequenceName = "posts_seq",
        allocationSize = 1
)
@Getter @Setter
@Table(name = "posts")
@NoArgsConstructor
@EqualsAndHashCode
public class Post {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "posts_seq_generator")
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

    //==연관관계 메서드==//
    public void conMember(Member member) {
        this.setMember(member);
        member.getPost().add(this);
    }

    //==생성 메서드==//
    public static Post createPost(Member member, String title, String contents) {
        Post post = new Post();
        post.setTitle(title);
        post.setContents(contents);
        post.setLastUpdateTime(LocalDateTime.now());
        post.conMember(member);
        return post;
    }
}
