package org.iclass.mvc.controller;


import lombok.extern.log4j.Log4j2;
import org.iclass.mvc.service.CommunityService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.annotation.RequestScope;

import java.util.List;

@Controller
@RequestMapping("/sample")
@Log4j2
public class SampleController {

	@GetMapping("/hello")
	public void hello(Model model) {
		model.addAttribute("message","하이 스프링");
		model.addAttribute("list", List.of("모모","나연","nana","쯔위"));
	}

	@GetMapping("/spring")
	public void spring(
			@RequestParam(required = false) String name,
			@RequestParam(required = false) Integer age
	)
	{
		log.info("파라미터 name : {}", name);
		log.info("파라미터 age : {}", age);
		//required = false로 하면 파라미터가 null이 되어야하므로
		//int, long 들은 Integer, Long과 같이 래퍼 타입으로 선언
	}
	

	
}
