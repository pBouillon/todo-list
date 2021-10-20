package io.pbouillon.todolist.application.commons.cqrs;

/**
 * Represent a CQRS command, an intent to alter the data
 * @param <TReturned> The return type of the operation when handled
 */
public interface Command<TReturned> extends CqrsOperation<TReturned> { }
