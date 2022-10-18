package SJ.ToDoList.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import SJ.ToDoList.entity.Todo;

@Repository
public interface ToDoListRepository extends JpaRepository<Todo, Long>{
	
	List<Todo> findByUserIdAndRegdate(Long id, String regdate);

	List<Todo> findByUserId(Long id);

	Todo findByIdAndUserId(Long todoid, Long id);
}
