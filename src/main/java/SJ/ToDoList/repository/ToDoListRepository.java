package SJ.ToDoList.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import SJ.ToDoList.entity.Member;
import SJ.ToDoList.entity.ToDoList;

@Repository
public interface ToDoListRepository extends JpaRepository<ToDoList, Long>{

	List<ToDoList> findAllById(Long id);


}
