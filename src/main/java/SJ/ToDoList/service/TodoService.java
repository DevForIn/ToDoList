package SJ.ToDoList.service;

import java.util.List;

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

	public void saveList(Todo todo) {
		toDoListRepository.save(todo);		
	}

	public List<Todo> findByUserIdAndRegdate(Long id, String regdate) {
		return toDoListRepository.findByUserIdAndRegdate(id,regdate);
	}	
}
