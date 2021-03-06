package io.pbouillon.todolist.infrastructure.mappers;

import io.pbouillon.todolist.application.items.commands.CreateTodoItemCommand;
import io.pbouillon.todolist.application.items.commands.ReplaceTodoItemCommand;
import io.pbouillon.todolist.application.items.dtos.TodoItemDto;
import io.pbouillon.todolist.domain.entities.TodoItem;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import java.util.List;

/**
 * {@link TodoItem} mappings to DTO and others
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TodoItemMapper {

    /**
     * Convert a {@link CreateTodoItemCommand} to a {@link TodoItem}
     * @param command The {@link CreateTodoItemCommand} to map
     * @return Its associated {@link TodoItem}
     */
    // Generated implementation does map all the properties,
    // but I suspect that using a record messed with IDEA detection
    @SuppressWarnings("UnmappedTargetProperties")
    TodoItem fromCommand(CreateTodoItemCommand command);

    /**
     * Convert a {@link TodoItem} to a DTO
     * @param todoItem The {@link TodoItem} to map
     * @return Its associated DTO
     */
    TodoItemDto toDto(TodoItem todoItem);

    /**
     * Convert a list of {@link TodoItem}s to a list of dtos
     * @param todoItems The {@link TodoItem}s to map
     * @return Their associated DTOs
     */
    List<TodoItemDto> toDtos(List<TodoItem> todoItems);

    /**
     * Replace the content of an existing {@link TodoItem} with the content of the {@link ReplaceTodoItemCommand}
     * @param todoItem The item whose content is to be replaced
     * @param command The command from which the values will be replaced
     */
    TodoItem updateFromCommand(@MappingTarget TodoItem todoItem, ReplaceTodoItemCommand command);

}
