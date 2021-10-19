package io.pbouillon.todolist.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

/**
 * Represent a task
 */
@Document
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TodoItem {

    /**
     * The item's id in the persisted storage
     */
    @Id
    private String id = "";

    /**
     * The main header of the task
     */
    private String title = "";

    /**
     * A longer description or additional information about the task (can be null)
     */
    private String commentary;

    /**
     * The task's creation date, represented as UTC
     */
    private Instant createdOn = Instant.now();

    /**
     * Whether this task has been completed or not
     */
    private boolean isDone;

}
