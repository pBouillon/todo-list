package io.pbouillon.todolist.infrastructure.mappers;

import io.pbouillon.todolist.application.items.commands.CreateTodoItemCommand;
import io.pbouillon.todolist.application.items.dtos.TodoItemDto;
import io.pbouillon.todolist.domain.entities.TodoItem;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * {@link TodoItem} mappings to DTO and others
 */
@Mapper
public interface TodoItemMapper {

    /**
     * Convert a {@link CreateTodoItemCommand} to a {@link TodoItem}
     * @param command The {@link CreateTodoItemCommand} to map
     * @return Its associated {@link TodoItem}
     */
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

}
