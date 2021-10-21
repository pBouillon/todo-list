package io.pbouillon.todolist.application.items.services;

import io.pbouillon.todolist.application.commons.cqrs.Command;
import io.pbouillon.todolist.application.items.commands.CreateTodoItemCommand;
import io.pbouillon.todolist.application.items.commands.DeleteTodoItemCommand;
import io.pbouillon.todolist.application.items.dtos.TodoItemDto;
import io.pbouillon.todolist.domain.entities.TodoItem;

/**
 * The service in charge of handling the {@link Command} regarding the {@link TodoItem}s
 *
 * The subsequent separation is made to respect the CQRS principles
 * see: https://www.baeldung.com/cqrs-for-a-spring-rest-api
 *
 * @see TodoItemService
 */
public interface TodoItemCommandService {

    /**
     * Create a new {@link TodoItem}
     * @param command The payload from which the {@link TodoItem} will be created
     * @return The {@link TodoItem} created, as a {@link TodoItemDto}
     */
    TodoItemDto createTodoItem(CreateTodoItemCommand command);

    /**
     * Delete a specific {@link TodoItem}
     * @param command The associated {@link DeleteTodoItemCommand}
     */
    Void deleteTodoItem(DeleteTodoItemCommand command);

}
