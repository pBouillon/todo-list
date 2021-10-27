package io.pbouillon.todolist.presentation.controllers.items;

import io.pbouillon.todolist.application.commons.cqrs.PageableQuery;
import io.pbouillon.todolist.domain.entities.TodoItem;
import io.pbouillon.todolist.infrastructure.persistence.repositories.TodoItemRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Unit test suite for the {@link TodoItemReadController}
 * @see TodoItemReadController
 */
@SpringBootTest
@AutoConfigureMockMvc
public class TodoItemReadControllerTests {

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
    @DisplayName("Given some todo items, when querying all items, then it should return an HTTP 200")
    public void givenSomeTodoItems_WhenQueryingAllItems_ThenItShouldReturnAnHTTP200() throws Exception {
        List<TodoItem> items = List.of(
                new TodoItem(),
                new TodoItem(),
                new TodoItem()
        );

        Mockito.when(todoItemRepository.findAll(any(PageRequest.class)))
                .thenReturn(new PageImpl<>(items));

        mockMvc.perform(get("/api/todos"))
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        jsonPath("$.content", hasSize(items.size())),
                        jsonPath("$.totalPages").value(1),
                        jsonPath("$.totalElements").value(items.size()),
                        jsonPath("$.number").value(PageableQuery.FIRST_PAGE_OFFSET)
                );
    }

    @Test
    @DisplayName("Given an unknown todo item id, when querying it, then it should return an HTTP 404")
    public void givenAnUnknownTodoItemId_WhenQueryingIt_ThenItShouldReturnAnHTTP404() throws Exception {
        Mockito.when(todoItemRepository.findById(anyString()))
                .thenReturn(Optional.empty());

        mockMvc.perform(get("/api/todos/" + UUID.randomUUID()))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Given a todo item id, when querying it, then it should return an HTTP 200")
    public void givenATodoItemId_WhenQueryingIt_ThenItShouldReturnAnHTTP200() throws Exception {
        TodoItem item = new TodoItem();
        item.setId(UUID.randomUUID().toString());

        Mockito.when(todoItemRepository.findById(anyString()))
                .thenReturn(Optional.of(item));

        mockMvc.perform(get("/api/todos/1"))
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        jsonPath("$.id").value(item.getId())
                );
    }

}