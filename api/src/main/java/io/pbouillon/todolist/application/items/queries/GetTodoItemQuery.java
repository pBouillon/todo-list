package io.pbouillon.todolist.application.items.queries;

import io.pbouillon.todolist.application.commons.cqrs.CqrsOperation;
import io.pbouillon.todolist.application.commons.cqrs.Query;
import io.pbouillon.todolist.application.items.dtos.TodoItemDto;
import io.pbouillon.todolist.domain.entities.TodoItem;

/**
 * {@link CqrsOperation} representing the intent of retrieving a specific {@link TodoItem} based on its id
 */
public record GetTodoItemQuery(String id) implements Query<TodoItemDto> { }
