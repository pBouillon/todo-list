package io.pbouillon.todolist.domain.enums;

import io.pbouillon.todolist.domain.entities.TodoItem;

/**
 * Represent the overall progression of a {@link TodoItem}
 */
public enum Status {

    /**
     * Represent a task not yet started
     */
    NotStarted,

    /**
     * Represent a task already started but not yet finished
     */
    InProgress,

    /**
     * Represent a task that cannot be completed
     */
    Blocked,

    /**
     * Represent a task that has already be done
     */
    Done,

}
