package com.firstHomePage.myBoard.web;

import com.firstHomePage.myBoard.domain.Post;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class commentForm {

    private Long id;

    private String contents;
    private LocalDateTime lastUpdateTime;
}
