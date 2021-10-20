package io.pbouillon.todolist.presentation.controllers.items;

import io.pbouillon.todolist.application.commons.cqrs.Query;
import io.pbouillon.todolist.application.items.TodoItemDispatcher;
import io.pbouillon.todolist.application.items.dtos.TodoItemDto;
import io.pbouillon.todolist.application.items.queries.GetTodoItemQuery;
import io.pbouillon.todolist.application.items.queries.GetTodoItemsQuery;
import io.pbouillon.todolist.domain.entities.TodoItem;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
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
     * @param todoItemDispatcher The service in charge of handling the {@link Query} regarding the {@link TodoItem}s
     */
    @Autowired
    public TodoItemReadController(TodoItemDispatcher todoItemDispatcher) {
        super (todoItemDispatcher);
    }

    /**
     * Fetch all {@link TodoItem}s
     * @return The persisted {@link TodoItem}s as {@link TodoItemDto}s
     */
    @GetMapping
    public ResponseEntity<List<TodoItemDto>> getTodoItems() {
        List<TodoItemDto> todoItems = todoItemDispatcher.handle(new GetTodoItemsQuery());

        return ResponseEntity.ok()
                .body(todoItems);
    }

    /**
     * Fetch a specific {@link TodoItem}
     * @return The serialized {@link TodoItem} as {@link TodoItemDto}
     */
    @GetMapping("/{id}")
    public ResponseEntity<TodoItemDto> getTodoItem(
            @ApiParam(value = "Id of the task to retrieve")
            @PathVariable String id) {
        TodoItemDto dto = todoItemDispatcher.handle(new GetTodoItemQuery(id));

        return ResponseEntity.ok()
                .body(dto);
    }

}
