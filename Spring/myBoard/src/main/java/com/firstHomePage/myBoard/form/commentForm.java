package com.firstHomePage.myBoard.form;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class commentForm {

    private Long id;

    private String contents;
    private LocalDateTime lastUpdateTime;
}
