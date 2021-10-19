package io.pbouillon.todolist.presentation.controllers;

import io.pbouillon.todolist.domain.entities.TodoItem;
import io.pbouillon.todolist.infrastructure.persistence.repositories.TodoItemRepository;
import io.pbouillon.todolist.presentation.dtos.TodoItemDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

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
     * @return The persisted {@link TodoItem}s as {@link TodoItemDto}s
     */
    @GetMapping
    public ResponseEntity<List<TodoItemDto>> getTodoItems() {
        List<TodoItemDto> todoItems = todoItemRepository.findAll()
                .stream()
                .map(TodoItemDto::FromTodoItem)
                .collect(Collectors.toList());

        log.info("Retrieved {} todo items", todoItems.size());

        return ResponseEntity.ok()
                .body(todoItems);
    }

    /**
     * Fetch a specific {@link TodoItem}
     * @return The serialized {@link TodoItem} as {@link TodoItemDto}
     */
    @GetMapping("/{id}")
    public ResponseEntity<TodoItemDto> getTodoItem(@PathVariable String id) {
        TodoItem todoItem = todoItemRepository.findById(id).orElseThrow();

        log.info("{} Retrieved", todoItem);

        TodoItemDto dto = TodoItemDto.FromTodoItem(todoItem);
        return ResponseEntity.ok()
                .body(dto);
    }

    /**
     * Create a new {@link TodoItem} from the payload
     * @param todoItem The {@link TodoItem} to create
     * @return The created resource as {@link TodoItemDto}
     */
    @PostMapping
    public ResponseEntity<TodoItemDto> post(@RequestBody TodoItem todoItem) {
        TodoItem created = todoItemRepository.save(todoItem);
        TodoItemDto dto = TodoItemDto.FromTodoItem(created);

        log.info("{} Created", dto);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.getId())
                .toUri();

        return ResponseEntity.created(location)
                .body(dto);
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
