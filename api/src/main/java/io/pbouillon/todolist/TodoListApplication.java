package io.pbouillon.todolist;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * Spring Web API entry-point
 */
@SpringBootApplication
@EnableMongoRepositories
public class TodoListApplication {

	/**
	 * Spring Web API entry-point
	 */
	public static void main(String[] args) {
		SpringApplication.run(TodoListApplication.class, args);
	}

}
