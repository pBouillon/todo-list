package io.pbouillon.todolist.application.items.services;

import io.pbouillon.todolist.application.items.TodoItemDispatcher;
import io.pbouillon.todolist.application.items.commands.CreateTodoItemCommand;
import io.pbouillon.todolist.application.items.commands.DeleteTodoItemCommand;
import io.pbouillon.todolist.application.items.dtos.TodoItemDto;
import io.pbouillon.todolist.domain.entities.TodoItem;
import io.pbouillon.todolist.infrastructure.mappers.TodoItemMapper;
import io.pbouillon.todolist.infrastructure.persistence.repositories.TodoItemRepository;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Unit test suite for the {@link TodoItemCommandService}
 * @see TodoItemCommandService
 */
@ExtendWith(MockitoExtension.class)
public class TodoItemCommandServiceTest {

    /**
     * The mocked {@link TodoItemMapper} to inject into the tested {@link TodoItemMapper} instances
     */
    @Mock
    TodoItemMapper todoItemMapper;

    /**
     * The mocked {@link TodoItemRepository} to inject into the tested {@link TodoItemDispatcher} instances
     */
    @Mock
    TodoItemRepository todoItemRepository;

    @Test
    @DisplayName("Given a CreateTodoItemCommand, when creating the item, then it should have been saved")
    public void givenACreateTodoItemCommand_WhenCreatingTheItem_ThenItShouldHaveBeenSaved() {
        Mockito.when(todoItemRepository.save(isA(TodoItem.class)))
                .thenReturn(new TodoItem());

        Mockito.when(todoItemMapper.fromCommand(isA(CreateTodoItemCommand.class)))
                .thenReturn(new TodoItem());

        Mockito.when(todoItemMapper.toDto(isA(TodoItem.class)))
                .thenReturn(new TodoItemDto());

        CreateTodoItemCommand command = new CreateTodoItemCommand(
                "title-" + UUID.randomUUID(),
                "description-" + UUID.randomUUID(),
                false
        );

        TodoItemCommandService todoItemService = new TodoItemService(todoItemMapper, todoItemRepository);
        todoItemService.createTodoItem(command);

        SoftAssertions.assertSoftly(softly -> {
            verify(todoItemMapper, times(1))
                    .toDto(isA(TodoItem.class));

            verify(todoItemRepository, times(1))
                    .save(isA(TodoItem.class));
        });
    }

    @Test
    @DisplayName("Given a DeleteTodoItemCommand, when deleting the item, then it should have been deleted")
    public void givenADeleteTodoItemCommand_WhenDeletingTheItem_ThenItShouldHaveBeenDeleted() {
        Mockito.when(todoItemRepository.findById(isA(String.class)))
                        .thenReturn(Optional.of(new TodoItem()));

        Mockito.doNothing()
                .when(todoItemRepository)
                .delete(isA(TodoItem.class));

        DeleteTodoItemCommand command = new DeleteTodoItemCommand("id-" + UUID.randomUUID());

        TodoItemCommandService todoItemService = new TodoItemService(todoItemMapper, todoItemRepository);
        todoItemService.deleteTodoItem(command);

        verify(todoItemRepository, times(1))
                .delete(isA(TodoItem.class));
    }

    @Test
    @DisplayName("Given a DeleteTodoItemCommand, when deleting an non-existing item, then an exception should be thrown")
    public void givenADeleteTodoItemCommand_WhenDeletingANonExistingItem_ThenAnExceptionShouldBeThrown() {
        Mockito.when(todoItemRepository.findById(isA(String.class)))
                .thenReturn(Optional.empty());

        DeleteTodoItemCommand command = new DeleteTodoItemCommand("id-" + UUID.randomUUID());

        TodoItemCommandService todoItemService = new TodoItemService(todoItemMapper, todoItemRepository);

        assertThatExceptionOfType(NoSuchElementException.class)
                .isThrownBy(() -> todoItemService.deleteTodoItem(command));
    }

}
