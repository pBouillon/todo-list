package io.pbouillon.todolist.application.items.services;

import io.pbouillon.todolist.application.items.commands.CreateTodoItemCommand;
import io.pbouillon.todolist.application.items.commands.DeleteTodoItemCommand;
import io.pbouillon.todolist.application.items.commands.ReplaceTodoItemCommand;
import io.pbouillon.todolist.application.items.dtos.TodoItemDto;
import io.pbouillon.todolist.application.items.queries.GetTodoItemQuery;
import io.pbouillon.todolist.application.items.queries.GetTodoItemsQuery;
import io.pbouillon.todolist.domain.entities.TodoItem;
import io.pbouillon.todolist.infrastructure.mappers.TodoItemMapper;
import io.pbouillon.todolist.infrastructure.persistence.repositories.TodoItemRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

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
    @CacheEvict(cacheNames = { "item" })
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
    @CacheEvict(cacheNames = { "items", "item" }, key = "#command.id()")
    @Override
    public Void deleteTodoItem(DeleteTodoItemCommand command) {
        TodoItem todoItem = todoItemRepository.findById(command.id()).orElseThrow();
        todoItemRepository.delete(todoItem);

        log.info("{} Deleted", todoItem);

        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Cacheable(cacheNames = { "item" }, key = "#query.id()")
    @Override
    public TodoItemDto getTodoItem(GetTodoItemQuery query) {
        TodoItem item = todoItemRepository.findById(query.id()).orElseThrow();

        log.info("Retrieved {}", item);

        return todoItemMapper.toDto(item);
    }

    /**
     * {@inheritDoc}
     */
    @Cacheable(cacheNames = { "items" }, key = "#query.getPageOffset()-#query.getItemsPerPages()")
    @Override
    public Page<TodoItemDto> getTodoItems(GetTodoItemsQuery query) {
        Page<TodoItem> items = todoItemRepository.findAll(query.getPageRequest());

        log.info("Retrieved {} todo items across {} pages", items.getNumberOfElements(), items.getTotalPages());

        return items.map(todoItemMapper::toDto);
    }

    /**
     * {@inheritDoc}
     */
    @CacheEvict(cacheNames = { "items", "item" }, key = "#command.id()")
    @Override
    public TodoItemDto replaceTodoItem(ReplaceTodoItemCommand command) {
        TodoItem item = todoItemRepository.findById(command.getId()).orElseThrow();

        log.info("Updating the content of {} from the {}", item, command);

        TodoItem updated = todoItemMapper.updateFromCommand(item, command);
        todoItemRepository.save(updated);

        return todoItemMapper.toDto(updated);
    }

}
