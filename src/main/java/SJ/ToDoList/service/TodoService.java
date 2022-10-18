package SJ.ToDoList.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import SJ.ToDoList.entity.Todo;
import SJ.ToDoList.repository.ToDoListRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TodoService {
    
	@Autowired
	private ToDoListRepository toDoListRepository;

	public void save(Todo todo) {
		toDoListRepository.save(todo);		
	}

	public List<Todo> findByUserIdAndRegdate(Long id, String regdate) {
		return toDoListRepository.findByUserIdAndRegdate(id,regdate);
	}
		
	public Optional<Todo> findById(Long id) {
		return toDoListRepository.findById(id);
	}

	public Todo findByIdAndUserId(Long todoid, Long id) {
		return toDoListRepository.findByIdAndUserId(todoid,id);
	}

	public void deleteById(Long todoid) {
		toDoListRepository.deleteById(todoid);	
	}

	public List<Todo> findByUserId(Long id) {
		return toDoListRepository.findByUserId(id);
	}
}
