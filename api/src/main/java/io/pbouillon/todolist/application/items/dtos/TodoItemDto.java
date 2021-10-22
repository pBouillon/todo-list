package io.pbouillon.todolist.application.items.dtos;

import io.pbouillon.todolist.domain.enums.Status;
import io.swagger.annotations.ApiModelProperty;
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

    @ApiModelProperty(notes = "The item's id in the persisted storage")
    private String id;

    @ApiModelProperty(notes = "The main header of the task")
    private String title;

    @ApiModelProperty(notes = "A longer description or additional information about the task (can be null)")
    private String note;

    @ApiModelProperty(notes = "The task's creation date, represented as UTC")
    private Instant createdOn;

    @ApiModelProperty(notes = "The current item's status")
    private Status status;

}
