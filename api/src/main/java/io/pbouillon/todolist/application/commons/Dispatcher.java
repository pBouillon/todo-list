package io.pbouillon.todolist.application.commons;

import io.pbouillon.todolist.application.commons.cqrs.CqrsOperation;

/**
 * Dispatch the given {@link CqrsOperation} to their handlers
 */
public interface Dispatcher {

    /**
     * Dispatch the given {@link CqrsOperation} to its handler
     * @param operation The operation to dispatch
     * @param <TReturned> The return type of the operation
     * @return The result of the operation's handling
     */
    <TReturned> TReturned handle(CqrsOperation<TReturned> operation);

}
