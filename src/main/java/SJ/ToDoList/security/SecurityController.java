package SJ.ToDoList.security;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/security")
public class SecurityController {

	@Autowired
	private SecurityService securityService;
	
	@GetMapping("/create/token")
	public Map<String,Object> createToken(@RequestParam(value="email") String email){
		String token = securityService.createToken(email, (2*2000*60));
		Map<String,Object> map = new LinkedHashMap<>();
		map.put("result", token);
		return map;
	}
	
	@GetMapping("/get/subject")
	public Map<String,Object> getSubject(@RequestParam(value="token") String token){
		String email = securityService.getSubject(token);
		Map<String,Object> map = new LinkedHashMap<>();
		map.put("result", email);
		
		return map;
	}
}
