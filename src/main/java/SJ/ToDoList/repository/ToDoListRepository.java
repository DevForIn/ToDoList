package SJ.ToDoList.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import SJ.ToDoList.entity.Todo;

@Repository
public interface ToDoListRepository extends JpaRepository<Todo, Long>{
	
	@SuppressWarnings("unchecked")
	Todo save(Todo todo);

	List<Todo> findByUserIdAndRegdate(Long id, String regdate);
	
}
