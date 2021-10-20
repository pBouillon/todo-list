package io.pbouillon.todolist.application.items.queries;

import io.pbouillon.todolist.application.commons.cqrs.CqrsOperation;
import io.pbouillon.todolist.application.commons.cqrs.Query;
import io.pbouillon.todolist.application.items.dtos.TodoItemDto;
import io.pbouillon.todolist.domain.entities.TodoItem;

import java.util.List;

/**
 * {@link CqrsOperation} representing the intent of retrieving all {@link TodoItem}s
 */
public record GetTodoItemsQuery() implements Query<List<TodoItemDto>> { }
