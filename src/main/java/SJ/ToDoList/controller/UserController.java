package SJ.ToDoList.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import SJ.ToDoList.entity.LoginVO;
import SJ.ToDoList.entity.User;
import SJ.ToDoList.security.SecurityService;
import SJ.ToDoList.service.UserService;

@RestController
@RequestMapping("ToDo")
public class UserController {	
	
	private final UserService userService;	
	private final SecurityService securityService;
	
	@Autowired
	public UserController(UserService userService, SecurityService securityService) {
		this.userService = userService;
		this.securityService = securityService;
	}
	
	// 회원가입 API
	@PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody User user) {
		Optional<User> findMember = userService.findByEmail(user.getEmail());
		if(!findMember.isEmpty()) {	
			return new ResponseEntity<>("회원가입 실패-아이디 중복",HttpStatus.BAD_REQUEST);
		}
		else {			
			userService.save(user);
			return new ResponseEntity<>("회원가입 완료",HttpStatus.OK);
		}		
	}
	
	// 로그인
	@PostMapping("/login")	
	public ModelAndView loginMember(@PathParam(value = "email") String email, @PathParam(value = "password") String password) throws URISyntaxException{
		ModelAndView mav = new ModelAndView();
		System.out.println(email);
		System.out.println(password);
		Optional<User> loginUser = userService.findByEmail(email);
		mav.setViewName("/test2");
		if(loginUser.isEmpty()) {
			mav.addObject("nodab", "로그인 실패 : [계정 없음]");
			return mav;
		}
		if(!password.equals(loginUser.get().getPassword())){
			mav.addObject("nodab", "로그인 실패 : [password 불일치]");
			return mav;
		} else {
			String token = securityService.generateToken(loginUser.get().getEmail());
			mav.addObject("token", token);
			System.out.println(loginUser.get().toString());
			mav.addObject("loginUser", loginUser.get());
			return mav;
		}
	}
}
