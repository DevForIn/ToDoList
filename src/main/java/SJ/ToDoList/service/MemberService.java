package SJ.ToDoList.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import SJ.ToDoList.entity.Member;
import SJ.ToDoList.entity.Todo;
import SJ.ToDoList.repository.MemberRepository;
import SJ.ToDoList.repository.ToDoListRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {
    
	
	@Autowired
	private MemberRepository memberRepository;
	
	@Autowired
	private ToDoListRepository toDoListRepository;

	@Transactional
	public Member save(Member member) {
		return memberRepository.save(member);		
	}

	public Optional<Member> findById(Long id) {
		return memberRepository.findById(id);		
	}

	public Optional<Member> findByEmail(String email) {
		return memberRepository.findByEmail(email);		
	}

	public List<Todo> findByList(Long id) {
		return toDoListRepository.findAllById(id);
	}

	
	
}
