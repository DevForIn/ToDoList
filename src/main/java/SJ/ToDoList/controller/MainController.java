package SJ.ToDoList.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
		
	// 메인
	@GetMapping("/")
	public String mainPage() {
		return "test.html";
	}
	
	// 테스트
	@GetMapping("/test")
	public String testPage() {
		return "test2.html";
	}
}
 