package io.pbouillon.todolist.application.items.commands;

import io.pbouillon.todolist.application.commons.cqrs.Command;
import io.pbouillon.todolist.application.commons.cqrs.CqrsOperation;
import io.pbouillon.todolist.domain.entities.TodoItem;

/**
 * {@link CqrsOperation} representing the intent of deleting a specific {@link TodoItem}
 */
public record DeleteTodoItemCommand(String id) implements Command<Void> { }
