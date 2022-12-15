package SJ.ToDoList.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "User")
public class User{
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ID")
	private Long id;
	
	@Setter
	@Column(nullable = false)
	private String email;

	@Setter
	@Column(nullable = false)
	private String name;
	
	@Setter
	@Column(nullable = false)
	private String password;

}
