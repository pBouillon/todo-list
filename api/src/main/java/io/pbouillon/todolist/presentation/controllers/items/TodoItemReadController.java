package io.pbouillon.todolist.presentation.controllers.items;

import io.pbouillon.todolist.domain.entities.TodoItem;
import io.pbouillon.todolist.infrastructure.mappers.TodoItemMapper;
import io.pbouillon.todolist.infrastructure.persistence.repositories.TodoItemRepository;
import io.pbouillon.todolist.presentation.dtos.TodoItemDto;
import io.swagger.annotations.Api;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Log4j2
@RestController
@RequestMapping(path = "/api/todos", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(tags = { TodoItemController.TAG }, produces = MediaType.APPLICATION_JSON_VALUE)
public class TodoItemReadController extends TodoItemController {

    /**
     * Controller's default constructor
     * @param repository Data access object to interact with the persisted {@link TodoItem} entities
     * @param mapper {@link TodoItem} mapper
     */
    @Autowired
    public TodoItemReadController(TodoItemRepository repository, TodoItemMapper mapper) {
        super (repository, mapper);
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

}
