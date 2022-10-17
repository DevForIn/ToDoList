package SJ.ToDoList.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import SJ.ToDoList.entity.Member;
import SJ.ToDoList.repository.MemberRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {
    
	
	@Autowired
	private MemberRepository memberRepository;
	
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
}
