package com.firstHomePage.myBoard.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter @Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@SequenceGenerator(
        name = "comment_seq_generator",
        sequenceName = "comment_seq",
        allocationSize = 1
)
public class Comment extends BaseEntity{

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "comment_seq_generator")
    @Column(name="comment_id")
    private Long id;

    @NotEmpty
    private String contents;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    //==연관관계 메서드==//
    public void conPost(Post post) {
        this.post = post;
        post.getComments().add(this);
    }

    //==생성 메서드==//
    public static Comment createComment(Post post, String contents) {
        Comment comment = new Comment();
        comment.conPost(post);
        comment.setContents(contents);
        return comment;
    }
}
