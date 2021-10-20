package io.pbouillon.todolist.presentation.controllers.items;

import io.pbouillon.todolist.application.commons.Dispatcher;
import io.pbouillon.todolist.application.commons.cqrs.Query;
import io.pbouillon.todolist.domain.entities.TodoItem;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public abstract class TodoItemController {

    protected static final String TAG = "TodoItem";

    /**
     * The service in charge of handling the {@link Query} regarding the {@link TodoItem}s
     */
    protected final Dispatcher todoItemDispatcher;

}
