package io.pbouillon.todolist.presentation.controllers.items;

import io.pbouillon.todolist.application.commons.cqrs.Query;
import io.pbouillon.todolist.application.items.TodoItemDispatcher;
import io.pbouillon.todolist.application.items.commands.CreateTodoItemCommand;
import io.pbouillon.todolist.application.items.commands.DeleteTodoItemCommand;
import io.pbouillon.todolist.application.items.dtos.TodoItemDto;
import io.pbouillon.todolist.domain.entities.TodoItem;
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
     * @param todoItemDispatcher The service in charge of handling the {@link Query} regarding the {@link TodoItem}s
     */
    @Autowired
    public TodoItemWriteController(TodoItemDispatcher todoItemDispatcher) {
        super (todoItemDispatcher);
    }

    /**
     * Create a new {@link TodoItem} from the payload
     * @param command The {@link TodoItem} to create
     * @return The created resource as {@link TodoItemDto}
     */
    @PostMapping
    public ResponseEntity<TodoItemDto> post(@RequestBody CreateTodoItemCommand command) {
        TodoItemDto created = todoItemDispatcher.handle(command);

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
        DeleteTodoItemCommand command = new DeleteTodoItemCommand(id);
        todoItemDispatcher.handle(command);

        return ResponseEntity.noContent().build();
    }

}
