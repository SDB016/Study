package com.firstHomePage.myBoard.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor
@SequenceGenerator(
        name = "member_seq_generator",
        sequenceName = "member_seq",
        allocationSize = 1
)
@EqualsAndHashCode
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "member_seq_generator")
    @Column(name = "member_id")
    private Long id;

    @Column(length = 18) @NotEmpty
    private String loginId;

    @Column(length = 12) @NotEmpty
    private String loginPwd;

    @Column(length = 4) @NotEmpty
    private String name;

    @Column(length = 10) @NotEmpty
    private String nickname;

    @Column(length = 2)
    private int age;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Post> post = new ArrayList<>();

    public Member(@NotEmpty String loginId, @NotEmpty String loginPwd, @NotEmpty String name, @NotEmpty String nickname, int age) {
        this.loginId = loginId;
        this.loginPwd = loginPwd;
        this.name = name;
        this.nickname = nickname;
        this.age = age;
    }
}
