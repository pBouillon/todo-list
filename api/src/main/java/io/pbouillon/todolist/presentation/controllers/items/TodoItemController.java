package io.pbouillon.todolist.presentation.controllers.items;

import io.pbouillon.todolist.domain.entities.TodoItem;
import io.pbouillon.todolist.infrastructure.mappers.TodoItemMapper;
import io.pbouillon.todolist.infrastructure.persistence.repositories.TodoItemRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public abstract class TodoItemController {

    protected static final String TAG = "TodoItem";

    /**
     * Data access object to interact with the persisted {@link TodoItem} entities
     */
    protected final  TodoItemRepository todoItemRepository;

    /**
     * {@link TodoItem} mapper
     */
    protected final  TodoItemMapper todoItemMapper;

}
