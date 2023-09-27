package org.iclass.mvc.controller;

import org.iclass.mvc.service.CommunityService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/sample")	
//url이 community로 시작하면 DispatcherServlet 으로부터 CommunityController가 위임받아 처리함
public class SampleController {

	private final CommunityService service;
	
	private SampleController(CommunityService service) {
		this.service = service;
	}
	
	//URL 설계 : 글 목록 조회는 /community/list, 글쓰기 /community/write, 글읽기 /community/read
	
	@GetMapping("/hello")
	public void hello(Model model) {
		model.addAttribute("message","하이 스프링");
	}
	

	
}
