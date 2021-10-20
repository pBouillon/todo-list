package io.pbouillon.todolist.application.items.services;

import io.pbouillon.todolist.application.commons.cqrs.Unit;
import io.pbouillon.todolist.application.items.commands.CreateTodoItemCommand;
import io.pbouillon.todolist.application.items.commands.DeleteTodoItemCommand;
import io.pbouillon.todolist.application.items.dtos.TodoItemDto;
import io.pbouillon.todolist.application.items.queries.GetTodoItemQuery;
import io.pbouillon.todolist.application.items.queries.GetTodoItemsQuery;
import io.pbouillon.todolist.domain.entities.TodoItem;
import io.pbouillon.todolist.infrastructure.mappers.TodoItemMapper;
import io.pbouillon.todolist.infrastructure.persistence.repositories.TodoItemRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@SuppressWarnings("ClassCanBeRecord")
@Log4j2
@Service
public class TodoItemService implements TodoItemCommandService, TodoItemQueryService {

    /***
     * {@link TodoItem} mapper
     */
    private final TodoItemMapper todoItemMapper;

    /**
     * Data access object to interact with the persisted {@link TodoItem} entities
     */
    private final TodoItemRepository todoItemRepository;

    /**
     *
     * @param todoItemMapper {@link TodoItem} mapper
     * @param todoItemRepository Data access object to interact with the persisted {@link TodoItem} entities
     */
    @Autowired
    public TodoItemService(TodoItemMapper todoItemMapper, TodoItemRepository todoItemRepository) {
        this.todoItemMapper = todoItemMapper;
        this.todoItemRepository = todoItemRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TodoItemDto createTodoItem(CreateTodoItemCommand command) {
        TodoItem item = todoItemMapper.fromCommand(command);
        item = todoItemRepository.save(item);

        log.info("{} Created", item);

        return todoItemMapper.toDto(item);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Unit deleteTodoItem(DeleteTodoItemCommand command) {
        TodoItem todoItem = todoItemRepository.findById(command.id()).orElseThrow();
        todoItemRepository.delete(todoItem);

        log.info("{} Deleted", todoItem);

        return new Unit();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TodoItemDto getTodoItem(GetTodoItemQuery query) {
        TodoItem item = todoItemRepository.findById(query.id()).orElseThrow();

        log.info("Retrieved {} from {}", item, query);

        return todoItemMapper.toDto(item);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<TodoItemDto> getTodoItems(GetTodoItemsQuery query) {
        List<TodoItem> items = todoItemRepository.findAll();

        log.info("Retrieved {} todo items", items.size());

        return todoItemMapper.toDtos(items);
    }

}