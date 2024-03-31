package com.joyfulgarden.controller;

import org.springframework.web.bind.annotation.GetMapping;

public class HomeController {
	
	 @GetMapping("/")
	    public String showHomePage() {
	        return "index";
	    }

	    @GetMapping("/courses")
	    public String showCoursesPage() {
	        return "courses";
	    }

	    @GetMapping("/about")
	    public String showAboutPage() {
	        return "about";
	    }

	    @GetMapping("/contact")
	    public String showContactPage() {
	        return "contact";
	    }

}
