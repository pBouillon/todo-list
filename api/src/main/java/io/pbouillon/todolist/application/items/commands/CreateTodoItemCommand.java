package io.pbouillon.todolist.application.items.commands;

import io.pbouillon.todolist.application.commons.cqrs.Command;
import io.pbouillon.todolist.application.commons.cqrs.CqrsOperation;
import io.pbouillon.todolist.application.items.dtos.TodoItemDto;
import io.pbouillon.todolist.domain.entities.TodoItem;
import io.pbouillon.todolist.domain.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.*;

/**
 * {@link CqrsOperation} representing the intent of the creation of a new {@link TodoItem}
 */
@Data
@AllArgsConstructor
public class CreateTodoItemCommand implements Command<TodoItemDto> {

    /**
     * The main header of the task
     */
    @NotBlank
    @Size(min = TodoItem.TITLE_MIN_LENGTH, max = TodoItem.TITLE_MAX_LENGTH)
    private String title;

    /**
     * A longer description or additional information about the task (can be blank)
     */
    @Size(max = TodoItem.NOTE_MAX_LENGTH)
    private String note;

    /**
     * The current item's status
     */
    @NotNull
    private Status status;

}
