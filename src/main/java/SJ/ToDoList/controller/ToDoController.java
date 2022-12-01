package SJ.ToDoList.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
	
	// 로그인 user의 toDo list get
	@GetMapping("/list/{id}")
	public ResponseEntity<?> toDoList(@PathVariable(value = "id") Long id, @RequestHeader(value="token") String token){				
		Optional<Member> member = memberService.findById(id);	
		Map<String,Object> map = new HashMap<>();		
		String email = securityService.getEmailFromToken(token);
		if(member.isEmpty()) {
			map.put("message","로그인 정보 없음");
			return new ResponseEntity<>(map,HttpStatus.valueOf(404));
		}		
		if(!member.get().getEmail().equals(email)) {						
			return new ResponseEntity<>("로그인 정보 불일치",HttpStatus.valueOf(404));
		}	
		List<Todo> list = todoService.findByUserId(id);			
		return new ResponseEntity<>(list,HttpStatus.OK);		
	}
	
	// 로그인 user의 toDo list 중 선택한 게시글 get
	@GetMapping("/list/{id}/{todoid}")
	public ResponseEntity<?> toDoOne(@PathVariable(value = "id") Long id, @PathVariable(value = "todoid") Long todoid, @RequestHeader(value="token") String token){				
		Optional<Member> member = memberService.findById(id);	
		Optional<Todo> todo = todoService.findById(todoid);
		
		String email = securityService.getEmailFromToken(token);		
		Map<String,Object> map = new HashMap<>();		
		
		if(member.isEmpty() || todo.isEmpty()) {
			map.put("message","로그인 및 ToDo 정보 없음");
			return new ResponseEntity<>(map,HttpStatus.BAD_REQUEST);
		}
		if(!member.get().getEmail().equals(email)) {						
			return new ResponseEntity<>("로그인 정보 불일치",HttpStatus.BAD_REQUEST);
		}
		Todo todoGet = todoService.findByIdAndUserId(todoid,id);		
		return new ResponseEntity<>(todoGet,HttpStatus.OK);		
	}
	
	// todo 저장
	@PostMapping("/list/{id}")
	public ResponseEntity<?> createList(@PathVariable(value = "id") Long id, @RequestBody Todo todo, @RequestHeader(value="token") String token){
		Optional<Member> member = memberService.findById(id);
		String email = securityService.getEmailFromToken(token);		
		if(member.isEmpty()) {			
			return new ResponseEntity<>("로그인 정보 없음",HttpStatus.valueOf(404));
		}
		if(!member.get().getEmail().equals(email)) {			
			return new ResponseEntity<>("로그인 정보 불일치",HttpStatus.valueOf(404));
		}		
		todoService.save(todo);
		return new ResponseEntity<>("저장완료",HttpStatus.OK);
	}
	
	// todo 삭제
	@DeleteMapping("/list/{id}/{todoid}")
	public ResponseEntity<?> deleteList(@PathVariable(value="id") Long id, @PathVariable(value="todoid") Long todoid, @RequestHeader(value="token") String token){
		Optional<Member> member = memberService.findById(id);
		String email = securityService.getEmailFromToken(token);
		if(member.isEmpty()) {			
			return new ResponseEntity<>("로그인 정보 없음",HttpStatus.valueOf(404));
		}
		if(!member.get().getEmail().equals(email)) {						
			return new ResponseEntity<>("로그인 정보 불일치",HttpStatus.valueOf(404));
		}
		
		todoService.deleteById(todoid);
		return new ResponseEntity<>("삭제완료",HttpStatus.valueOf(200));
	}
}
