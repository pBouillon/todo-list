package io.pbouillon.todolist.presentation.controllers.items;

import io.pbouillon.todolist.application.items.commands.CreateTodoItemCommand;
import io.pbouillon.todolist.domain.entities.TodoItem;
import io.pbouillon.todolist.domain.enums.Status;
import io.pbouillon.todolist.infrastructure.persistence.repositories.TodoItemRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Unit test suite for the {@link TodoItemWriteController}
 * @see TodoItemWriteController
 */
@SpringBootTest
@AutoConfigureMockMvc
public class TodoItemWriteControllerTests {

    /**
     * The mocked {@link TodoItemRepository} to inject into the tested {@link TodoItemRepository} instances
     */
    @MockBean
    private TodoItemRepository todoItemRepository;

    /**
     * The HTTP test client calling the controller's endpoints
     */
    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Given a todo item, when posting it, then it should have been created")
    public void givenATodoItem_WhenPostingIt_ThenItShouldHaveBeenCreated() throws Exception {
        Mockito.when(todoItemRepository.save(isA(TodoItem.class)))
                .thenReturn(new TodoItem());

        CreateTodoItemCommand command = new CreateTodoItemCommand(
                "title",
                "description",
                Status.NotStarted
        );

        String payload = String.format("""
                { "title": "%s", "commentary": "%s", "status": "%s" }
                """,
                command.title(),
                command.commentary(),
                command.status()
        );

        mockMvc.perform(post("/api/todos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload))
                .andExpect(status().isCreated());

        verify(todoItemRepository, times(1))
                .save(isA(TodoItem.class));
    }

    @Test
    @DisplayName("Given an unknown todo item id, when deleting it, then a not found result should be returned")
    public void givenAnUnknownTodoItemId_whenDeletingIt_thenANotFoundResultShouldBeReturned() throws Exception {
        Mockito.when(todoItemRepository.findById(anyString()))
                .thenReturn(Optional.empty());

        mockMvc.perform(delete("/api/todos/" + UUID.randomUUID()))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Given a todo item id, when deleting it, then a no content result should be returned")
    public void givenATodoItemId_WhenDeletingIt_thenANoContentResultShouldBeReturned() throws Exception {
        Mockito.when(todoItemRepository.findById(anyString()))
                .thenReturn(Optional.of(new TodoItem()));

        doNothing()
                .when(todoItemRepository)
                .delete(any(TodoItem.class));

        mockMvc.perform(delete("/api/todos/" + UUID.randomUUID()))
                .andExpect(status().isNoContent());
    }

}
