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

import static java.lang.Math.max;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
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
    @DisplayName("Given a todo item, when posting it, then it should return an HTTP 201")
    public void givenATodoItem_WhenPostingIt_ThenItShouldReturnAnHTTP201() throws Exception {
        Mockito.when(todoItemRepository.save(isA(TodoItem.class)))
                .thenReturn(new TodoItem());

        CreateTodoItemCommand command = new CreateTodoItemCommand(
                "title",
                "note",
                Status.NotStarted
        );

        String payload = String.format("""
                { "title": "%s", "note": "%s", "status": "%s" }
                """,
                command.getTitle(),
                command.getNote(),
                command.getStatus()
        );

        mockMvc.perform(post("/api/todos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload))
                .andExpect(status().isCreated());

        verify(todoItemRepository, times(1))
                .save(isA(TodoItem.class));
    }

    @Test
    @DisplayName("Given a todo item with no title, when posting it, then it should return an HTTP 400")
    public void givenATodoItemWithNoTitle_WhenPostingIt_ThenItShouldReturnAnHTTP400() throws Exception {
        String payload = String.format("""
                { "title": "", "note": "%s", "status": "%s" }
                """,
                UUID.randomUUID(), Status.NotStarted
        );

        mockMvc.perform(post("/api/todos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpectAll(
                        status().isBadRequest(),
                        jsonPath("$.title").exists()
                );
    }

    @Test
    @DisplayName("Given a todo item with no status, when posting it, then it should return an HTTP 400")
    public void givenATodoItemWithNoStatus_WhenPostingIt_ThenItShouldReturnAnHTTP400() throws Exception {
        String payload = String.format("""
                { "title": "%s", "note": "%s" }
                """,
                UUID.randomUUID(), UUID.randomUUID()
        );

        mockMvc.perform(post("/api/todos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpectAll(
                        status().isBadRequest(),
                        jsonPath("$.status").exists()
                );
    }

    @Test
    @DisplayName("Given a todo item with a short title, when posting it, then it should return an HTTP 400")
    public void givenATodoItemWithAShortTitle_WhenPostingIt_ThenItShouldReturnAnHTTP400() throws Exception {
        String payload = String.format("""
                { "title": "%s", "note": "%s", "status": "%s" }
                """,
                "*".repeat(max(0, TodoItem.TITLE_MIN_LENGTH - 1)),
                UUID.randomUUID(),
                Status.NotStarted
        );

        mockMvc.perform(post("/api/todos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpectAll(
                        status().isBadRequest(),
                        jsonPath("$.title").exists()
                );
    }

    @Test
    @DisplayName("Given a todo item with a long title, when posting it, then it should return an HTTP 400")
    public void givenATodoItemWithALongTitle_WhenPostingIt_ThenItShouldReturnAnHTTP400() throws Exception {
        String payload = String.format("""
                { "title": "%s", "note": "%s", "status": "%s" }
                """,
                "*".repeat(TodoItem.TITLE_MAX_LENGTH + 1),
                UUID.randomUUID(),
                Status.NotStarted
        );

        mockMvc.perform(post("/api/todos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpectAll(
                        status().isBadRequest(),
                        jsonPath("$.title").exists()
                );
    }

    @Test
    @DisplayName("Given a todo item with a long note, when posting it, then it should return an HTTP 400")
    public void givenATodoItemWithALongNote_WhenPostingIt_ThenItShouldReturnAnHTTP400() throws Exception {
        String payload = String.format("""
                { "title": "%s", "note": "%s", "status": "%s" }
                """,
                UUID.randomUUID(),
                "*".repeat(TodoItem.NOTE_MAX_LENGTH + 1),
                Status.NotStarted
        );

        mockMvc.perform(post("/api/todos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpectAll(
                        status().isBadRequest(),
                        jsonPath("$.note").exists()
                );
    }

    @Test
    @DisplayName("Given an unknown todo item id, when deleting it, then a not found result should return an HTTP 404")
    public void givenAnUnknownTodoItemId_WhenDeletingIt_ThenItShouldReturnAnHTTP404() throws Exception {
        Mockito.when(todoItemRepository.findById(anyString()))
                .thenReturn(Optional.empty());

        mockMvc.perform(delete("/api/todos/" + UUID.randomUUID()))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Given a todo item id, when deleting it, then it should return an HTTP 204")
    public void givenATodoItemId_WhenDeletingIt_ThenItShouldReturnAnHTTP204() throws Exception {
        Mockito.when(todoItemRepository.findById(anyString()))
                .thenReturn(Optional.of(new TodoItem()));

        doNothing()
                .when(todoItemRepository)
                .delete(any(TodoItem.class));

        mockMvc.perform(delete("/api/todos/" + UUID.randomUUID()))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Given an unknown todo item id, when replacing it, then a not found result should return an HTTP 404")
    public void givenAnUnknownTodoItemId_WhenReplacingIt_ThenItShouldReturnAnHTTP404() throws Exception {
        Mockito.when(todoItemRepository.findById(anyString()))
                .thenReturn(Optional.empty());

        final String id = UUID.randomUUID().toString();

        String payload = String.format("""
                { "id": "%s", "title": "%s", "note": "%s", "status": "%s" }
                """,
                id,
                UUID.randomUUID(),
                UUID.randomUUID(),
                Status.NotStarted
        );

        mockMvc.perform(put("/api/todos/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Given a todo item id and a replace command with a mismatching id, when replacing it, then a not found result should return an HTTP 400")
    public void givenATodoItemIdAndAReplaceCommandWithAMismatching_WhenReplacingIt_ThenItShouldReturnAnHTTP400() throws Exception {
        final String id = UUID.randomUUID().toString();
        final String mismatchingId = id + "-";

        String payload = String.format("""
                { "id": "%s", "title": "%s", "note": "%s", "status": "%s" }
                """,
                id,
                UUID.randomUUID(),
                UUID.randomUUID(),
                Status.NotStarted
        );

        mockMvc.perform(put("/api/todos/" + mismatchingId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Given a todo item with no title, when replacing it, then it should return an HTTP 400")
    public void givenATodoItemWithNoTitle_WhenReplacingIt_ThenItShouldReturnAnHTTP400() throws Exception {
        final String id = UUID.randomUUID().toString();

        String payload = String.format("""
                { "id": "%s", "title": "", "note": "%s", "status": "%s" }
                """,
                id, UUID.randomUUID(), Status.NotStarted
        );

        mockMvc.perform(put("/api/todos/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpectAll(
                        status().isBadRequest(),
                        jsonPath("$.title").exists()
                );
    }

    @Test
    @DisplayName("Given a todo item with no status, when replacing it, then it should return an HTTP 400")
    public void givenATodoItemWithNoStatus_WhenReplacingIt_ThenItShouldReturnAnHTTP400() throws Exception {
        final String id = UUID.randomUUID().toString();

        String payload = String.format("""
                { "id": "%s", "title": "%s", "note": "%s" }
                """,
                id, UUID.randomUUID(), UUID.randomUUID()
        );

        mockMvc.perform(put("/api/todos/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpectAll(
                        status().isBadRequest(),
                        jsonPath("$.status").exists()
                );
    }

    @Test
    @DisplayName("Given a todo item with a short title, when replacing it, then it should return an HTTP 400")
    public void givenATodoItemWithAShortTitle_WhenReplacingIt_ThenItShouldReturnAnHTTP400() throws Exception {
        final String id = UUID.randomUUID().toString();

        String payload = String.format("""
                { "id": "%s", "title": "%s", "note": "%s", "status": "%s" }
                """,
                id,
                "*".repeat(max(0, TodoItem.TITLE_MIN_LENGTH - 1)),
                UUID.randomUUID(),
                Status.NotStarted
        );

        mockMvc.perform(put("/api/todos/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpectAll(
                        status().isBadRequest(),
                        jsonPath("$.title").exists()
                );
    }

    @Test
    @DisplayName("Given a todo item with a long title, when replacing it, then it should return an HTTP 400")
    public void givenATodoItemWithALongTitle_WhenReplacingIt_ThenItShouldReturnAnHTTP400() throws Exception {
        final String id = UUID.randomUUID().toString();

        String payload = String.format("""
                { "id": "%s", "title": "%s", "note": "%s", "status": "%s" }
                """,
                id,
                "*".repeat(TodoItem.TITLE_MAX_LENGTH + 1),
                UUID.randomUUID(),
                Status.NotStarted
        );

        mockMvc.perform(put("/api/todos/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpectAll(
                        status().isBadRequest(),
                        jsonPath("$.title").exists()
                );
    }

    @Test
    @DisplayName("Given a todo item with a long note, when replacing it, then it should return an HTTP 400")
    public void givenATodoItemWithALongNote_WhenReplacingIt_ThenItShouldReturnAnHTTP400() throws Exception {
        final String id = UUID.randomUUID().toString();

        String payload = String.format("""
                { "id": "%s", "title": "%s", "note": "%s", "status": "%s" }
                """,
                id,
                UUID.randomUUID(),
                "*".repeat(TodoItem.NOTE_MAX_LENGTH + 1),
                Status.NotStarted
        );

        mockMvc.perform(put("/api/todos/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpectAll(
                        status().isBadRequest(),
                        jsonPath("$.note").exists()
                );
    }

}
