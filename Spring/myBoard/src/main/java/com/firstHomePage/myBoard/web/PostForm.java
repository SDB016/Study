package com.firstHomePage.myBoard.web;

import com.firstHomePage.myBoard.domain.Member;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter @Setter
public class PostForm {

    private Long id;

    @NotEmpty(message = "제목을 작성해주세요.")
    private String title;

    @NotEmpty(message = "내용을 작성해주세요.")
    private String contents;

}
