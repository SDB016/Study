package com.firstHomePage.myBoard;

import com.firstHomePage.myBoard.util.Session;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Optional;

@EnableJpaAuditing
@SpringBootApplication
public class MyBoardApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyBoardApplication.class, args);
	}

	@Bean
	public AuditorAware<String> auditorProvider(){
		return ()->{
			ServletRequestAttributes attributes
					= (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();

			String currentUser = (String) attributes.getRequest().getSession().getAttribute(Session.SESSION_ID);

			if(currentUser != null) return Optional.of(currentUser);
			else return Optional.of("Anonymous");
		};
	}
}
