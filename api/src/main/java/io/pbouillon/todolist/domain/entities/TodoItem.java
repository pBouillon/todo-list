package io.pbouillon.todolist.domain.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.time.Instant;

/**
 * Represent a task
 */
@Data
@NoArgsConstructor
@Document
public class TodoItem implements Serializable {

    /**
     * The item's id in the persisted storage
     */
    @Id
    private String id;

    /**
     * The main header of the task
     */
    private String title;

    /**
     * Additional, longer description of the task
     */
    private String description;

    /**
     * The task's creation date, represented as UTC
     */
    private Instant createdOn = Instant.now();

    /**
     * Whether this task has been completed or not
     */
    private boolean isDone;

}
