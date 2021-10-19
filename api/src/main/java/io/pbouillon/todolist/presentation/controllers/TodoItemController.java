package io.pbouillon.todolist.presentation.controllers;

import io.pbouillon.todolist.domain.entities.TodoItem;
import io.pbouillon.todolist.infrastructure.persistence.repositories.TodoItemRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

/**
 * REST controller dedicated to the {@link TodoItem} resources
 */
@Slf4j
@RestController
@RequestMapping(
        path = "/api/todos",
        produces = MediaType.APPLICATION_JSON_VALUE)
public class TodoItemController {

    /**
     * Data access object to interact with the persisted {@link TodoItem} entities
     */
    private final TodoItemRepository todoItemRepository;

    /**
     * Controller's default constructor
     * @param repository Data access object to interact with the persisted {@link TodoItem} entities
     */
    @Autowired
    public TodoItemController(TodoItemRepository repository) {
        todoItemRepository = repository;
    }

    /**
     * Fetch all {@link TodoItem}s
     * @return The persisted {@link TodoItem}s
     */
    @GetMapping
    public ResponseEntity<List<TodoItem>> getTodoItems() {
        List<TodoItem> todoItems = todoItemRepository.findAll();

        log.info("Retrieved {} todo items", todoItems.size());

        return ResponseEntity.ok()
                .body(todoItems);
    }

    /**
     * Fetch a specific {@link TodoItem}
     * @return The serialized {@link TodoItem}
     */
    @GetMapping("/{id}")
    public ResponseEntity<TodoItem> getTodoItem(@PathVariable String id) {
        TodoItem todoItem = todoItemRepository.findById(id).orElseThrow();

        log.info("{} Retrieved", todoItem);

        return ResponseEntity.ok()
                .body(todoItem);
    }

    /**
     * Create a new {@link TodoItem} from the payload
     * @param todoItem The {@link TodoItem} to create
     * @return The created resource
     */
    @PostMapping
    public ResponseEntity<TodoItem> post(@RequestBody TodoItem todoItem) {
        TodoItem created = todoItemRepository.save(todoItem);

        log.info("{} Created", created);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.getId())
                .toUri();

        return ResponseEntity.created(location)
                .body(created);
    }

    /**
     * Delete a {@link TodoItem}
     * @param id The id of the resource to delete
     * @return No content on success
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> removeTodoItem(@PathVariable String id) {
        TodoItem todoItem = todoItemRepository.findById(id).orElseThrow();
        todoItemRepository.delete(todoItem);

        log.info("{} Deleted", todoItem);

        return ResponseEntity.noContent().build();
    }

}
