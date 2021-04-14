package com.firstHomePage.myBoard.config;

import com.firstHomePage.myBoard.interceptor.CommentAuthInterceptor;
import com.firstHomePage.myBoard.interceptor.LoginInterceptor;
import com.firstHomePage.myBoard.interceptor.PostAuthIntercepter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebserviceConfig implements WebMvcConfigurer {

    private final LoginInterceptor loginInterceptor;
    private final PostAuthIntercepter postAuthIntercepter;
    private final CommentAuthInterceptor commentAuthInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/post/**")
                .addPathPatterns("/comment/**");

        registry.addInterceptor(postAuthIntercepter)
                .addPathPatterns("/post/**");

        registry.addInterceptor(commentAuthInterceptor)
                .addPathPatterns("/post/*/comment/**");
    }
}
