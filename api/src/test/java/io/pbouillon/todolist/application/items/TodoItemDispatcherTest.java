package io.pbouillon.todolist.application.items;

import io.pbouillon.todolist.application.commons.Dispatcher;
import io.pbouillon.todolist.application.items.commands.CreateTodoItemCommand;
import io.pbouillon.todolist.application.items.commands.DeleteTodoItemCommand;
import io.pbouillon.todolist.application.items.queries.GetTodoItemQuery;
import io.pbouillon.todolist.application.items.queries.GetTodoItemsQuery;
import io.pbouillon.todolist.application.items.services.TodoItemService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
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
        CreateTodoItemCommand command = new CreateTodoItemCommand(
                "title-" + UUID.randomUUID(),
                "description-" + UUID.randomUUID(),
                false
        );

        Mockito.when(todoItemService.createTodoItem(any(CreateTodoItemCommand.class)))
                .thenReturn(null);

        Dispatcher dispatcher = new TodoItemDispatcher(todoItemService);
        dispatcher.handle(command);

        verify(todoItemService, times(1))
                .createTodoItem(any(CreateTodoItemCommand.class));
    }

    @Test
    @DisplayName("Given a DeleteTodoItemCommand, when dispatching it, then the correct method should have been called")
    public void givenADeleteTodoItemCommand_WhenDispatchingIt_ThenTheCorrectMethodShouldHaveBeenCalled() {
        DeleteTodoItemCommand command = new DeleteTodoItemCommand("id-" + UUID.randomUUID());
        Mockito.when(todoItemService.deleteTodoItem(any(DeleteTodoItemCommand.class)))
                .thenReturn(null);

        Dispatcher dispatcher = new TodoItemDispatcher(todoItemService);
        dispatcher.handle(command);

        verify(todoItemService, times(1))
                .deleteTodoItem(any(DeleteTodoItemCommand.class));
    }

    @Test
    @DisplayName("Given a GetTodoItemQuery, when dispatching it, then the correct method should have been called")
    public void givenAGetTodoItemQuery_WhenDispatchingIt_ThenTheCorrectMethodShouldHaveBeenCalled() {
        GetTodoItemQuery query = new GetTodoItemQuery("id-" + UUID.randomUUID());
        Mockito.when(todoItemService.getTodoItem(any(GetTodoItemQuery.class)))
                .thenReturn(null);

        Dispatcher dispatcher = new TodoItemDispatcher(todoItemService);
        dispatcher.handle(query);

        verify(todoItemService, times(1))
                .getTodoItem(any(GetTodoItemQuery.class));
    }

    @Test
    @DisplayName("Given a GetTodoItemsQuery, when dispatching it, then the correct method should have been called")
    public void givenAGetTodoItemsQuery_WhenDispatchingIt_ThenTheCorrectMethodShouldHaveBeenCalled() {
        GetTodoItemsQuery query = new GetTodoItemsQuery();
        Mockito.when(todoItemService.getTodoItems(any(GetTodoItemsQuery.class)))
                .thenReturn(null);

        Dispatcher dispatcher = new TodoItemDispatcher(todoItemService);
        dispatcher.handle(query);

        verify(todoItemService, times(1))
                .getTodoItems(any(GetTodoItemsQuery.class));
    }

}
