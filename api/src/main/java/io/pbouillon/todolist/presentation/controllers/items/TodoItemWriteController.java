package io.pbouillon.todolist.presentation.controllers.items;

import io.pbouillon.todolist.application.commons.cqrs.Query;
import io.pbouillon.todolist.application.items.TodoItemDispatcher;
import io.pbouillon.todolist.application.items.commands.CreateTodoItemCommand;
import io.pbouillon.todolist.application.items.commands.DeleteTodoItemCommand;
import io.pbouillon.todolist.application.items.commands.ReplaceTodoItemCommand;
import io.pbouillon.todolist.application.items.dtos.TodoItemDto;
import io.pbouillon.todolist.domain.entities.TodoItem;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
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
    public ResponseEntity<TodoItemDto> createTodoItem(
            @ApiParam("Payload from which the item will be created")
            @Valid @RequestBody CreateTodoItemCommand command) {
        TodoItemDto created = todoItemDispatcher.handle(command);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.getId())
                .toUri();

        return ResponseEntity.created(location)
                .body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TodoItemDto> replaceTodoItem(
            @ApiParam(value = "Id of the item to replace")
            @PathVariable String id,
            @Valid @RequestBody ReplaceTodoItemCommand command) {
        if (!id.equals(command.getId())) {
            return ResponseEntity.badRequest().build();
        }

        TodoItemDto replaced = todoItemDispatcher.handle(command);
        return ResponseEntity.ok(replaced);
    }

    /**
     * Delete a {@link TodoItem}
     * @param id The id of the resource to delete
     * @return No content on success
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> removeTodoItem(
            @ApiParam(value = "Id of the item to delete")
            @PathVariable String id) {
        DeleteTodoItemCommand command = new DeleteTodoItemCommand(id);
        todoItemDispatcher.handle(command);

        return ResponseEntity.noContent().build();
    }

}
