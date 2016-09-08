package cn.arvix.angularjs.seo.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import springfox.documentation.annotations.ApiIgnore;

@ApiIgnore
@Controller()
public class HomeController {
	@RequestMapping("/")
	public String home(HttpServletResponse response) {
		response.setContentType("text/html; charset=UTF-8");
		return "swagger.html";
	}
	
	
}
