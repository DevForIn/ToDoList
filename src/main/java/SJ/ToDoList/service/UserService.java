package SJ.ToDoList.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import SJ.ToDoList.entity.User;
import SJ.ToDoList.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    
	
	@Autowired
	private UserRepository memberRepository;
	
	@Transactional
	public User save(User user) {
		return memberRepository.save(user);		
	}

	public Optional<User> findById(Long id) {
		return memberRepository.findById(id);		
	}

	public Optional<User> findByEmail(String email) {
		return memberRepository.findByEmail(email);		
	}	
}
