package SJ.ToDoList.controller;

import java.util.Optional;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import SJ.ToDoList.entity.AuthToken;
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
	
	// 회원가입 GetMapping
	@GetMapping("/signup")
	public ModelAndView signUpmain() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/signup");
		return mav;
	}
	
	// 회원가입 API
	@PostMapping("/signup")
    public ModelAndView signup(@PathParam(value="email") String email, @PathParam(value="name") String name, @PathParam(value="password") String password) {
		ModelAndView mav = new ModelAndView();
		Optional<User> findMember = userService.findByEmail(email);
		if(!findMember.isEmpty()) {	
			mav.addObject("nodab","회원가입 실패-아이디 중복");
			return mav;
		}
		else {
			User user = new User(null,email,name,password);
			userService.save(user);
			mav.setViewName("/test");
			return mav;
		}		
	}
	
	// 로그인
	@PostMapping("/login")	
	public ModelAndView loginMember(@PathParam(value = "email") String email, @PathParam(value = "password") String password){
		ModelAndView mav = new ModelAndView();
		Optional<User> loginUser = userService.findByEmail(email);
		mav.setViewName("redirect:/ToDo/list/"+loginUser.get().getId());
		
		
		if(loginUser.isEmpty()) {
			mav.addObject("nodab", "로그인 실패 : [계정 없음]");
			return mav;
		}
		if(!password.equals(loginUser.get().getPassword())){
			mav.addObject("nodab", "로그인 실패 : [password 불일치]");
			return mav;
		} else {
			String token1 = securityService.generateToken(loginUser.get().getEmail());
			
			AuthToken token = new AuthToken(token1);
			mav.addObject("loginUser", loginUser.get().getName());
			mav.addObject("token", token.getToken());
			System.out.println(mav.getViewName());
			
			return mav;
		}
	}
}

