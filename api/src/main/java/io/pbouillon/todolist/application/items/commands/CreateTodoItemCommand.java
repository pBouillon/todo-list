package io.pbouillon.todolist.application.items.commands;

import io.pbouillon.todolist.application.commons.cqrs.Command;
import io.pbouillon.todolist.application.commons.cqrs.CqrsOperation;
import io.pbouillon.todolist.application.items.dtos.TodoItemDto;
import io.pbouillon.todolist.domain.entities.TodoItem;
import io.pbouillon.todolist.domain.enums.Status;

/**
 * {@link CqrsOperation} representing the intent of the creation of a new {@link TodoItem}
 */
public record CreateTodoItemCommand(
        String title,
        String commentary,
        Status status
) implements Command<TodoItemDto> { }
