package io.pbouillon.todolist.application.items.queries;

import io.pbouillon.todolist.application.commons.cqrs.PageableQuery;
import io.pbouillon.todolist.application.commons.cqrs.CqrsOperation;
import io.pbouillon.todolist.application.items.dtos.TodoItemDto;
import io.pbouillon.todolist.domain.entities.TodoItem;
import lombok.ToString;
import org.springframework.data.domain.Page;

/**
 * {@link CqrsOperation} representing the intent of retrieving all {@link TodoItem}s
 */
@ToString(callSuper = true)
public class GetTodoItemsQuery extends PageableQuery<Page<TodoItemDto>> { }
