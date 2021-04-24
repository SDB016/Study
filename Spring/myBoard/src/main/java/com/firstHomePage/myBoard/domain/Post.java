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
@EqualsAndHashCode(callSuper = false)
public class Post extends BaseEntity{

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "posts_seq_generator")
    @Column(name="post_id")
    private Long id;

    @NotEmpty
    private String title;
    @NotEmpty
    private String contents;
    private int views;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

    //==연관관계 메서드==//

    //==생성 메서드==//
    public static Post createPost(String title, String contents) {
        Post post = new Post();
        post.setTitle(title);
        post.setContents(contents);
        return post;
    }
}
