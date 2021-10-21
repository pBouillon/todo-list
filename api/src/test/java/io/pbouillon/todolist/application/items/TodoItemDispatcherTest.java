package io.pbouillon.todolist.application.items;

import io.pbouillon.todolist.application.commons.Dispatcher;
import io.pbouillon.todolist.application.items.commands.CreateTodoItemCommand;
import io.pbouillon.todolist.application.items.commands.DeleteTodoItemCommand;
import io.pbouillon.todolist.application.items.queries.GetTodoItemQuery;
import io.pbouillon.todolist.application.items.queries.GetTodoItemsQuery;
import io.pbouillon.todolist.application.items.services.TodoItemService;
import io.pbouillon.todolist.domain.enums.Status;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Unit test suite for the {@link TodoItemDispatcher}
 * @see TodoItemDispatcher
 */
@ExtendWith(MockitoExtension.class)
public class TodoItemDispatcherTest {

    /**
     * The mocked {@link TodoItemService} to inject into the tested {@link TodoItemDispatcher} instances
     */
    @Mock
    TodoItemService todoItemService;

    @Test
    @DisplayName("Given a CreateTodoItemCommand, when dispatching it, then the correct method should have been called")
    public void givenACreateTodoItemCommand_WhenDispatchingIt_ThenTheCorrectMethodShouldHaveBeenCalled() {
        Mockito.when(todoItemService.createTodoItem(isA(CreateTodoItemCommand.class)))
                .thenReturn(null);

        CreateTodoItemCommand command = new CreateTodoItemCommand(
                "title-" + UUID.randomUUID(),
                "description-" + UUID.randomUUID(),
                Status.NotStarted
        );

        Dispatcher dispatcher = new TodoItemDispatcher(todoItemService);
        dispatcher.handle(command);

        verify(todoItemService, times(1))
                .createTodoItem(isA(CreateTodoItemCommand.class));
    }

    @Test
    @DisplayName("Given a DeleteTodoItemCommand, when dispatching it, then the correct method should have been called")
    public void givenADeleteTodoItemCommand_WhenDispatchingIt_ThenTheCorrectMethodShouldHaveBeenCalled() {
        Mockito.when(todoItemService.deleteTodoItem(isA(DeleteTodoItemCommand.class)))
                .thenReturn(null);

        DeleteTodoItemCommand command = new DeleteTodoItemCommand("id-" + UUID.randomUUID());

        Dispatcher dispatcher = new TodoItemDispatcher(todoItemService);
        dispatcher.handle(command);

        verify(todoItemService, times(1))
                .deleteTodoItem(isA(DeleteTodoItemCommand.class));
    }

    @Test
    @DisplayName("Given a GetTodoItemQuery, when dispatching it, then the correct method should have been called")
    public void givenAGetTodoItemQuery_WhenDispatchingIt_ThenTheCorrectMethodShouldHaveBeenCalled() {
        Mockito.when(todoItemService.getTodoItem(isA(GetTodoItemQuery.class)))
                .thenReturn(null);

        GetTodoItemQuery query = new GetTodoItemQuery("id-" + UUID.randomUUID());

        Dispatcher dispatcher = new TodoItemDispatcher(todoItemService);
        dispatcher.handle(query);

        verify(todoItemService, times(1))
                .getTodoItem(isA(GetTodoItemQuery.class));
    }

    @Test
    @DisplayName("Given a GetTodoItemsQuery, when dispatching it, then the correct method should have been called")
    public void givenAGetTodoItemsQuery_WhenDispatchingIt_ThenTheCorrectMethodShouldHaveBeenCalled() {
        Mockito.when(todoItemService.getTodoItems(isA(GetTodoItemsQuery.class)))
                .thenReturn(null);

        GetTodoItemsQuery query = new GetTodoItemsQuery();

        Dispatcher dispatcher = new TodoItemDispatcher(todoItemService);
        dispatcher.handle(query);

        verify(todoItemService, times(1))
                .getTodoItems(isA(GetTodoItemsQuery.class));
    }

}
