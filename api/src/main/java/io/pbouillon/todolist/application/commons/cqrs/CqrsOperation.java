package io.pbouillon.todolist.application.commons.cqrs;

/**
 * Represent a CQRS operation
 * @param <TReturned> The return type of the operation when handled
 * @see Command
 * @see Query
 */
public interface CqrsOperation<TReturned> { }
