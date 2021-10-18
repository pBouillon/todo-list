package io.pbouillon.todolist.infrastructure.persistence.repositories;

import io.pbouillon.todolist.domain.entities.TodoItem;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Repository to access the persisted {@link TodoItem} entities
 */
public interface TodoItemRepository extends MongoRepository<TodoItem, String> {
}
