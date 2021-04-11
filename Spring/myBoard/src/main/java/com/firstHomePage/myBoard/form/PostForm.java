package com.firstHomePage.myBoard.form;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class PostForm {

    private Long id;

    @NotEmpty(message = "제목을 작성해주세요.")
    private String title;

    @NotEmpty(message = "내용을 작성해주세요.")
    private String contents;

    private List<commentForm> commentForm = new ArrayList<>();
}
