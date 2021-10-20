package io.pbouillon.todolist.presentation.controllers.items;

import io.pbouillon.todolist.application.commons.PageableQuery;
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
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<Page<TodoItemDto>> getTodoItems(
            @ApiParam(value = "Number of items to display per page")
            @RequestParam(defaultValue = "" + PageableQuery.ITEMS_PER_PAGE_DEFAULT_VALUE)
            int itemsPerPages,
            @ApiParam(value = "Offset of the page to retrieve")
            @RequestParam(defaultValue = "" + PageableQuery.FIRST_PAGE_OFFSET)
            int pageOffset
    ) {
        GetTodoItemsQuery query = new GetTodoItemsQuery();
        query.setItemsPerPages(itemsPerPages);
        query.setPageOffset(pageOffset);

        Page<TodoItemDto> todoItems = todoItemDispatcher.handle(query);

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
