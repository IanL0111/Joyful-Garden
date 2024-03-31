package com.joyfulgarden.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebAppConfig implements WebMvcConfigurer {

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/login/page").setViewName("login");
		registry.addViewController("/login/welcome").setViewName("welcome");
		registry.addViewController("/logout/page").setViewName("logout");
		registry.addViewController("/logout").setViewName("logoutResult");
	}

//	@Override
//	public void addResourceHandlers(ResourceHandlerRegistry registry) {
//		registry.addResourceHandler("/css/**").addResourceLocations("/WEB-INF/resources/css/");
//	}
   
}

