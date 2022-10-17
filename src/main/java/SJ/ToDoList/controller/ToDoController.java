package SJ.ToDoList.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import SJ.ToDoList.entity.Member;
import SJ.ToDoList.entity.Todo;
import SJ.ToDoList.security.SecurityService;
import SJ.ToDoList.service.MemberService;

@RestController
@RequestMapping("ToDo")
public class ToDoController {	
	
	private final MemberService memberService;	
	private final SecurityService securityService;
	
	@Autowired
	public ToDoController(MemberService memberService, SecurityService securityService) {
		this.memberService = memberService;
		this.securityService = securityService;
	}
	
	@GetMapping("/list/{id}")
	public ResponseEntity<List> toDoList(@RequestParam(value = "id") Long id ,@RequestHeader(value="token") String token){
		Optional<Member> member = memberService.findById(id);
		String email = securityService.getEmailFromToken(token);
		List<Todo> list = new ArrayList<>();		
		
		if(member.isEmpty()) {			
			return new ResponseEntity<>(list,HttpStatus.BAD_REQUEST);
		} 
		if(!member.get().getEmail().equals(email)) {						
			return new ResponseEntity<>(list,HttpStatus.BAD_REQUEST);
		}
		else {
			list = memberService.findByList(id);
			return new ResponseEntity<>(list,HttpStatus.OK);
		}
		
	}
}
