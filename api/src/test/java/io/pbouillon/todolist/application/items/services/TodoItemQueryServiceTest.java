package io.pbouillon.todolist.application.items.services;

import io.pbouillon.todolist.application.items.TodoItemDispatcher;
import io.pbouillon.todolist.application.items.dtos.TodoItemDto;
import io.pbouillon.todolist.application.items.queries.GetTodoItemQuery;
import io.pbouillon.todolist.application.items.queries.GetTodoItemsQuery;
import io.pbouillon.todolist.domain.entities.TodoItem;
import io.pbouillon.todolist.infrastructure.mappers.TodoItemMapper;
import io.pbouillon.todolist.infrastructure.persistence.repositories.TodoItemRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Unit test suite for the {@link TodoItemQueryService}
 * @see TodoItemQueryService
 */
@ExtendWith(MockitoExtension.class)
public class TodoItemQueryServiceTest {

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
    @DisplayName("Given a GetTodoItemQuery, when retrieving the item, then it should have been retrieved")
    public void givenAGetTodoItemQuery_WhenRetrievingTheItem_ThenItShouldHaveBeenRetrieved() {
        Mockito.when(todoItemRepository.findById(isA(String.class)))
                .thenReturn(Optional.of(new TodoItem()));

        Mockito.when(todoItemMapper.toDto(isA(TodoItem.class)))
                .thenReturn(new TodoItemDto());

        GetTodoItemQuery query = new GetTodoItemQuery("id-" + UUID.randomUUID());

        TodoItemQueryService todoItemService = new TodoItemService(todoItemMapper, todoItemRepository);
        todoItemService.getTodoItem(query);

        verify(todoItemRepository, times(1))
                .findById(isA(String.class));

        verify(todoItemMapper, times(1))
                .toDto(isA(TodoItem.class));
    }

    @Test
    @DisplayName("Given a GetTodoItemQuery, when retrieving a non-existing  item, then an exception should be thrown")
    public void givenAGetTodoItemQuery_WhenRetrievingANonExistingItem_ThenAnExceptionShouldBeThrown() {
        Mockito.when(todoItemRepository.findById(isA(String.class)))
                .thenReturn(Optional.empty());

        GetTodoItemQuery query = new GetTodoItemQuery("id-" + UUID.randomUUID());

        TodoItemQueryService todoItemService = new TodoItemService(todoItemMapper, todoItemRepository);

        assertThatExceptionOfType(NoSuchElementException.class)
                .isThrownBy(() -> todoItemService.getTodoItem(query));
    }

    @Test
    @DisplayName("Given a GetTodoItemsQuery, when retrieving the items, then they should have been retrieved")
    public void givenAGetTodoItemsQuery_WhenRetrievingTheItems_ThenTheyShouldHaveBeenRetrieved() {
        Mockito.when(todoItemRepository.findAll())
                .thenReturn(new ArrayList<>());

        Mockito.when(todoItemMapper.toDtos(anyList()))
                .thenReturn(new ArrayList<>());

        GetTodoItemsQuery query = new GetTodoItemsQuery();

        TodoItemQueryService todoItemService = new TodoItemService(todoItemMapper, todoItemRepository);
        todoItemService.getTodoItems(query);

        verify(todoItemRepository, times(1))
                .findAll();

        verify(todoItemMapper, times(1))
                .toDtos(anyList());
    }
}
