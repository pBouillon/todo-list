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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@Log4j2
@Api(tags = { TodoItemController.TAG })
@RestController
@RequestMapping(path = "/api/todos", produces = MediaType.APPLICATION_JSON_VALUE)
public class TodoItemWriteController extends TodoItemController {

    /**
     * Controller's default constructor
     * @param repository Data access object to interact with the persisted {@link TodoItem} entities
     * @param mapper {@link TodoItem} mapper
     */
    @Autowired
    public TodoItemWriteController(TodoItemRepository repository, TodoItemMapper mapper) {
        super (repository, mapper);
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
