package io.pbouillon.todolist.presentation.dtos;

import io.pbouillon.todolist.domain.entities.TodoItem;
import lombok.Data;

import java.time.Instant;

/**
 * {@link io.pbouillon.todolist.domain.entities.TodoItem} Data Transfer Object to be served by the API
 */
@Data
public class TodoItemDto {

    /**
     * The item's id in the persisted storage
     */
    private String id;

    /**
     * The main header of the task
     */
    private String title;

    /**
     * A longer description or additional information about the task (can be null)
     */
    private String commentary;

    /**
     * The task's creation date, represented as UTC
     */
    private Instant createdOn;

    /**
     * Whether this task has been completed or not
     */
    private boolean isDone;

    /**
     * Create a new DTO from the domain object
     * @param todoItem The {@link TodoItem} to be mapped
     * @return The associated {@link TodoItemDto}
     */
    public static TodoItemDto FromTodoItem(TodoItem todoItem) {
        TodoItemDto dto = new TodoItemDto();

        dto.setId(todoItem.getId());
        dto.setTitle(todoItem.getTitle());
        dto.setCommentary(todoItem.getCommentary());
        dto.setCreatedOn(todoItem.getCreatedOn());
        dto.setDone(todoItem.isDone());

        return dto;
    }

}
