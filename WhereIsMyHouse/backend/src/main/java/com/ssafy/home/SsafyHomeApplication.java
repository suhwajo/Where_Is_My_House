package com.ssafy.home;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@Configuration
@ComponentScan(basePackages = {"com.ssafy"})
@MapperScan(basePackages = {"com.ssafy.**.mapper"})
public class SsafyHomeApplication implements WebMvcConfigurer{

	public static void main(String[] args) {
		SpringApplication.run(SsafyHomeApplication.class, args);
	}
	
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
			.allowedOrigins("*")
			.allowedMethods(
					HttpMethod.GET.name(),
					HttpMethod.POST.name(),
					HttpMethod.PUT.name(),
					HttpMethod.DELETE.name(),
					HttpMethod.HEAD.name(),
					HttpMethod.OPTIONS.name(),
					HttpMethod.PATCH.name())
			.maxAge(1800);
	}
}
