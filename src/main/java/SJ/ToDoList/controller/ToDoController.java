package SJ.ToDoList.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import SJ.ToDoList.entity.User;
import SJ.ToDoList.entity.AuthToken;
import SJ.ToDoList.entity.Todo;
import SJ.ToDoList.security.SecurityService;
import SJ.ToDoList.service.UserService;
import SJ.ToDoList.service.TodoService;

@RestController
@RequestMapping("ToDo")
public class ToDoController {	
	
	private final UserService userService;
	private final TodoService todoService;
	private final SecurityService securityService;
	
	@Autowired
	public ToDoController(UserService userService,TodoService todoService, SecurityService securityService) {
		this.userService = userService;
		this.securityService = securityService;
		this.todoService = todoService;
	}
	
	// 로그인 user의 toDo list get
	@GetMapping("/list/{id}")
	public ModelAndView toDoList(@PathVariable(value = "id") Long id, @Param(value="token") AuthToken token){				
		ModelAndView mav = new ModelAndView();
		Optional<User> user = userService.findById(id);	
		System.out.println("========> " + token.toString());
		System.out.println("========> " + token.getToken());
		
		String email = securityService.getEmailFromToken(token.getToken());
		mav.setViewName("/boardlist");
		if(user.isEmpty()) {
			mav.addObject("nodab", "게시글 조회 실패 : [로그인 정보 없음]");
			return mav;
		}		
		if(!user.get().getEmail().equals(email)) {						
			mav.addObject("nodab", "게시글 조회 실패 : [로그인 정보 불일치]");
			return mav;
		}
		
		List<Todo> list = todoService.findByUserId(id);
		mav.addObject("list", list);
		mav.addObject("token",token.getToken());
		return mav;
	}
	
	// 로그인 user의 toDo list 중 선택한 게시글 get
	@GetMapping("/list/{id}/{todoid}")
	public ResponseEntity<?> toDoOne(@PathVariable(value = "id") Long id, @PathVariable(value = "todoid") Long todoid, @RequestHeader(value="token") String token){				
		Optional<User> user = userService.findById(id);	
		Optional<Todo> todo = todoService.findById(todoid);
		
		String email = securityService.getEmailFromToken(token);		
		Map<String,Object> map = new HashMap<>();		
		
		if(user.isEmpty() || todo.isEmpty()) {
			map.put("message","로그인 및 ToDo 정보 없음");
			return new ResponseEntity<>(map,HttpStatus.BAD_REQUEST);
		}
		if(!user.get().getEmail().equals(email)) {						
			return new ResponseEntity<>("로그인 정보 불일치",HttpStatus.BAD_REQUEST);
		}
		Todo todoGet = todoService.findByIdAndUserId(todoid,id);		
		return new ResponseEntity<>(todoGet,HttpStatus.OK);		
	}
	
	// todo 저장
	@PostMapping("/list/{id}")
	public ResponseEntity<?> createList(@PathVariable(value = "id") Long id, @RequestBody Todo todo, @RequestHeader(value="token") String token){
		Optional<User> user = userService.findById(id);
		String email = securityService.getEmailFromToken(token);		
		if(user.isEmpty()) {			
			return new ResponseEntity<>("로그인 정보 없음",HttpStatus.valueOf(404));
		}
		if(!user.get().getEmail().equals(email)) {			
			return new ResponseEntity<>("로그인 정보 불일치",HttpStatus.valueOf(404));
		}		
		todoService.save(todo);
		return new ResponseEntity<>("저장완료",HttpStatus.OK);
	}
	
	// todo 삭제
	@DeleteMapping("/list/{id}/{todoid}")
	public ResponseEntity<?> deleteList(@PathVariable(value="id") Long id, @PathVariable(value="todoid") Long todoid, @RequestHeader(value="token") String token){
		Optional<User> user = userService.findById(id);
		String email = securityService.getEmailFromToken(token);
		if(user.isEmpty()) {			
			return new ResponseEntity<>("로그인 정보 없음",HttpStatus.valueOf(404));
		}
		if(!user.get().getEmail().equals(email)) {						
			return new ResponseEntity<>("로그인 정보 불일치",HttpStatus.valueOf(404));
		}
		
		todoService.deleteById(todoid);
		return new ResponseEntity<>("삭제완료",HttpStatus.valueOf(200));
	}
}
