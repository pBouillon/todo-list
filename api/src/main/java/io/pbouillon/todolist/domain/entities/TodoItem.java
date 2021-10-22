package io.pbouillon.todolist.domain.entities;

import io.pbouillon.todolist.domain.enums.Status;
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
     * The minimum length of the title field
     */
    public static final int TITLE_MIN_LENGTH = 3;

    /**
     * The maximum length of the title field
     */
    public static final int TITLE_MAX_LENGTH = 64;

    /**
     * The maximum length of the note field
     */
    public static final int NOTE_MAX_LENGTH = 512;

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
     * A longer description or additional information about the task (can be blank)
     */
    private String note;

    /**
     * The task's creation date, represented as UTC
     */
    private Instant createdOn = Instant.now();

    /**
     * The current item's status
     */
    private Status status = Status.NotStarted;

}
