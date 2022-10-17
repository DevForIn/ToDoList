package SJ.ToDoList.controller;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import SJ.ToDoList.entity.LoginVO;
import SJ.ToDoList.entity.Member;
import SJ.ToDoList.security.SecurityService;
import SJ.ToDoList.service.MemberService;

@RestController
@RequestMapping("ToDo")
public class MemberController {	
	
	private final MemberService memberService;
	
	private final SecurityService securityService;
	
	@Autowired
	public MemberController(MemberService memberService, SecurityService securityService) {
		this.memberService = memberService;
		this.securityService = securityService;
	}
		
	// 회원가입 API
	@PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody Member member) {
		Optional<Member> findMember = memberService.findByEmail(member.getEmail());
		if(!findMember.isEmpty()) {	
			return new ResponseEntity<>("회원가입 실패-아이디 중복",HttpStatus.BAD_REQUEST);
		}
		else {			
			memberService.save(member);
			return new ResponseEntity<>("회원가입 완료",HttpStatus.OK);
		}		
	}
	
	// 로그인
	@PostMapping("/login")	
	public ResponseEntity<Map> loginMember(@RequestBody LoginVO loginVo){
		Optional<Member> loginMember = memberService.findByEmail(loginVo.getEmail());
		Map<String,Object> map = new LinkedHashMap<>();
		if(loginMember.isEmpty()) {
			map.put("로그인 실패 -> ", "[계정 없음]");
			return new ResponseEntity<>(map,HttpStatus.BAD_REQUEST);
		}
		if(!loginVo.getPassword().equals(loginMember.get().getPassword())){
			map.put("로그인 실패 -> ", "[password 불일치]");
			return new ResponseEntity<>(map,HttpStatus.BAD_REQUEST);
		} else {
			
			String token = securityService.generateToken(loginMember.get().getEmail());
			map.put("token", token);
			return new ResponseEntity<>(map,HttpStatus.OK);
		}
	}
}
