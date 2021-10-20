package io.pbouillon.todolist.application.commons.cqrs;

/**
 * Represent a CQRS query, an intent to read the data
 * @param <TReturned> The return type of the operation when handled
 */
public interface Query<TReturned> extends CqrsOperation<TReturned>  { }
