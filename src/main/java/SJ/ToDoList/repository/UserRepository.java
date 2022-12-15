package SJ.ToDoList.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import SJ.ToDoList.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

	@SuppressWarnings("unchecked")
	User save(User user);

	Optional<User> findById(Long id);
	
	Optional<User> findByEmail(String email);

}
