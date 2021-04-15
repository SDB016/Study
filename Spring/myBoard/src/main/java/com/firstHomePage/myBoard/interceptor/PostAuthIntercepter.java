package com.firstHomePage.myBoard.interceptor;

import com.firstHomePage.myBoard.domain.Post;
import com.firstHomePage.myBoard.repository.PostRepository;
import com.firstHomePage.myBoard.util.Session;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class PostAuthIntercepter implements HandlerInterceptor {

    private final PostRepository postRepository;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String httpMethod = request.getMethod();

        if (httpMethod.equals("PATCH") || httpMethod.equals("DELETE")) {
            String sessionId = (String) request.getSession().getAttribute(Session.SESSION_ID);

            Map<?, ?> pathVariable = (Map<?, ?>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
            System.out.println("=============>> pathVariable = " + pathVariable);
            long id = Long.parseLong((String) pathVariable.get("id"));

            Post post = postRepository.findOne(id);
            String postWriter = post.getCreatedBy();

            if (!postWriter.equals(sessionId)) {
                response.getOutputStream().println("NOT AUTHORIZE!!");
                return false;
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
