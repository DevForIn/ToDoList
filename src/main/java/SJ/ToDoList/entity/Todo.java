package SJ.ToDoList.entity;

import java.time.LocalDateTime;

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
@Table(name = "Todo")
public class Todo{
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ID")
	private Long id;
	
	@Setter
	@Column(nullable = false)
	private String title;

	@Setter
	@Column(nullable = false)
	private String content;
	
	@Setter
	@Column(nullable = false)
	private String status;
	
	@Setter
	@Column(nullable = false)
	private LocalDateTime regdate;
	
	@Setter
	@Column(nullable = false)
	private String userId;

}
