package SJ.ToDoList.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import SJ.ToDoList.entity.Member;
import SJ.ToDoList.entity.Todo;

@Repository
public interface ToDoListRepository extends JpaRepository<Todo, Long>{

	List<Todo> findAllById(Long id);


}
