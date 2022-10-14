package SJ.ToDoList.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import SJ.ToDoList.entity.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long>{

	@SuppressWarnings("unchecked")
	Member save(Member member);

	Optional<Member> findById(Long id);
	
	Optional<Member> findByEmail(String email);

}
