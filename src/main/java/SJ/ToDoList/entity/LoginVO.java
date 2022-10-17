package SJ.ToDoList.entity;

import lombok.Data;

@Data
public class LoginVO {	
	private String email;
	private String password;
	private String regdate;
	private Long listId;
}
