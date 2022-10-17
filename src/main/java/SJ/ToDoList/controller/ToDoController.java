package SJ.ToDoList.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import SJ.ToDoList.entity.LoginVO;
import SJ.ToDoList.entity.Member;
import SJ.ToDoList.entity.Todo;
import SJ.ToDoList.security.SecurityService;
import SJ.ToDoList.service.MemberService;
import SJ.ToDoList.service.TodoService;

@RestController
@RequestMapping("ToDo")
public class ToDoController {	
	
	private final MemberService memberService;
	private final TodoService todoService;
	private final SecurityService securityService;
	
	@Autowired
	public ToDoController(MemberService memberService,TodoService todoService, SecurityService securityService) {
		this.memberService = memberService;
		this.securityService = securityService;
		this.todoService = todoService;
	}
	
	@GetMapping("/list/{id}")
	public ResponseEntity<List> toDoList(@PathVariable(value = "id") Long id, @RequestHeader(value="token") String token,@RequestBody LoginVO loginVo){
		Optional<Member> member = memberService.findById(id);		
		String email = securityService.getEmailFromToken(token);
		List<Todo> list = new ArrayList<>();	
		if(member.isEmpty()) {			
			return new ResponseEntity<>(list,HttpStatus.BAD_REQUEST);
		}
		if(!member.get().getEmail().equals(email)) {						
			return new ResponseEntity<>(list,HttpStatus.BAD_REQUEST);
		}		
		list = todoService.findByUserIdAndRegdate(member.get().getId(),loginVo.getRegdate());
		return new ResponseEntity<>(list,HttpStatus.OK);
		
	}
	
	@PostMapping("/list/{id}")
	public ResponseEntity<?> createList(@PathVariable(value = "id") Long id,@RequestBody Todo todo,@RequestHeader(value="token") String token){
		Optional<Member> member = memberService.findById(id);
		String email = securityService.getEmailFromToken(token);
		if(member.isEmpty()) {			
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		if(!member.get().getEmail().equals(email)) {						
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}		
		todoService.saveList(todo);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@DeleteMapping("/list/{id}")
	public ResponseEntity<?> deleteList(@PathVariable(value="id") Long id,@RequestBody Todo todo,@RequestHeader(value="token") String token){
		Optional<Member> member = memberService.findById(id);
		String email = securityService.getEmailFromToken(token);
		if(member.isEmpty()) {			
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		if(!member.get().getEmail().equals(email)) {						
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return null;
	}
}
