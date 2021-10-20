package io.pbouillon.todolist.application.items;

import io.pbouillon.todolist.application.commons.Dispatcher;
import io.pbouillon.todolist.application.commons.cqrs.Command;
import io.pbouillon.todolist.application.commons.cqrs.CqrsOperation;
import io.pbouillon.todolist.application.commons.cqrs.Query;
import io.pbouillon.todolist.application.items.commands.CreateTodoItemCommand;
import io.pbouillon.todolist.application.items.commands.DeleteTodoItemCommand;
import io.pbouillon.todolist.application.items.queries.GetTodoItemQuery;
import io.pbouillon.todolist.application.items.queries.GetTodoItemsQuery;
import io.pbouillon.todolist.application.items.services.TodoItemService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@SuppressWarnings({"unchecked", "ClassCanBeRecord"})
@Log4j2
@Service
public class TodoItemDispatcher implements Dispatcher {

    private final TodoItemService todoItemService;

    @Autowired
    public TodoItemDispatcher(TodoItemService todoItemService) {
        this.todoItemService = todoItemService;
    }

    @Override
    public <TReturned> TReturned handle(CqrsOperation<TReturned> operation) {
        log.info("Handling incoming operation: {}", operation);

        return operation instanceof Command
            ? handleCommand((Command<TReturned>) operation)
            : handleQuery((Query<TReturned>) operation);
    }

    /**
     * Dispatch the given {@link Command} to its handler
     * @param command The command to dispatch
     * @param <TReturned> The return type of the command
     * @return The result of the command's handling
     */
    private <TReturned> TReturned handleCommand(Command<TReturned> command) {
        if (command instanceof CreateTodoItemCommand createTodoItemCommand) {
            return (TReturned) todoItemService.createTodoItem(createTodoItemCommand);
        }

        if (command instanceof DeleteTodoItemCommand deleteTodoItemCommand) {
            return (TReturned) todoItemService.deleteTodoItem(deleteTodoItemCommand);
        }

        return null;
    }

    /**
     * Dispatch the given {@link Query} to its handler
     * @param query The query to dispatch
     * @param <TReturned> The return type of the query
     * @return The result of the query's handling
     */
    private <TReturned> TReturned handleQuery(Query<TReturned> query) {
        if (query instanceof GetTodoItemsQuery getTodoItemsQuery) {
            return (TReturned) todoItemService.getTodoItems(getTodoItemsQuery);
        }

        if (query instanceof GetTodoItemQuery getTodoItemQuery) {
            return (TReturned) todoItemService.getTodoItem(getTodoItemQuery);
        }

        return null;
    }

}
