package io.pbouillon.todolist.presentation.controllers;

import io.pbouillon.todolist.domain.entities.TodoItem;
import io.pbouillon.todolist.infrastructure.mappers.TodoItemMapper;
import io.pbouillon.todolist.infrastructure.persistence.repositories.TodoItemRepository;
import io.pbouillon.todolist.presentation.dtos.TodoItemDto;
import lombok.extern.log4j.Log4j2;
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
@Log4j2
@RestController
@RequestMapping(path = "/api/todos", produces = MediaType.APPLICATION_JSON_VALUE)
public class TodoItemController {

    /**
     * Data access object to interact with the persisted {@link TodoItem} entities
     */
    private final TodoItemRepository todoItemRepository;

    /**
     * {@link TodoItem} mapper
     */
    private final TodoItemMapper todoItemMapper;

    /**
     * Controller's default constructor
     * @param repository Data access object to interact with the persisted {@link TodoItem} entities
     */
    @Autowired
    public TodoItemController(TodoItemRepository repository, TodoItemMapper mapper) {
        todoItemRepository = repository;
        todoItemMapper = mapper;
    }

    /**
     * Fetch all {@link TodoItem}s
     * @return The persisted {@link TodoItem}s as {@link TodoItemDto}s
     */
    @GetMapping
    public ResponseEntity<List<TodoItemDto>> getTodoItems() {
        List<TodoItem> items = todoItemRepository.findAll();
        log.info("Retrieved {} todo items", items.size());

        List<TodoItemDto> todoItems = todoItemMapper.toDtos(items);
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

        TodoItemDto dto = todoItemMapper.toDto(todoItem);
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
        log.info("{} Created", created);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.getId())
                .toUri();

        TodoItemDto dto = todoItemMapper.toDto(created);
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
