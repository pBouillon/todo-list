package io.pbouillon.todolist;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * Spring Web API entry-point
 */
@SpringBootApplication
@EnableCaching
@EnableMongoRepositories
public class TodoListApplication {

	/**
	 * Spring Web API entry-point
	 */
	public static void main(String[] args) {
		SpringApplication.run(TodoListApplication.class, args);
	}

}
