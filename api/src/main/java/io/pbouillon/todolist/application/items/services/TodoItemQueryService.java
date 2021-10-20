package io.pbouillon.todolist.application.items.services;

import io.pbouillon.todolist.application.commons.cqrs.Query;
import io.pbouillon.todolist.application.items.dtos.TodoItemDto;
import io.pbouillon.todolist.application.items.queries.GetTodoItemQuery;
import io.pbouillon.todolist.application.items.queries.GetTodoItemsQuery;
import io.pbouillon.todolist.domain.entities.TodoItem;

import java.util.List;

/**
 * The service in charge of handling the {@link Query} regarding the {@link TodoItem}s
 *
 * The subsequent separation is made to respect the CQRS principles
 * see: https://www.baeldung.com/cqrs-for-a-spring-rest-api
 *
 * @see TodoItemService
 */
public interface TodoItemQueryService {

    /**
     * Get a specific {@link TodoItem}
     * @param query The associated CQRS query
     * @return The fetched {@link TodoItem} if any, as {@link TodoItemDto}
     */
    TodoItemDto getTodoItem(GetTodoItemQuery query);

    /**
     * Get all {@link TodoItem}s
     * @param query The associated CQRS query
     * @return The fetched {@link TodoItem}s, as a list of {@link TodoItemDto}s
     */
    List<TodoItemDto> getTodoItems(GetTodoItemsQuery query);

}
