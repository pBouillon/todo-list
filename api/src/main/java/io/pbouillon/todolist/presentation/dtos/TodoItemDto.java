package io.pbouillon.todolist.presentation.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.Instant;

/**
 * {@link io.pbouillon.todolist.domain.entities.TodoItem} Data Transfer Object to be served by the API
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TodoItemDto implements Serializable {

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

}
