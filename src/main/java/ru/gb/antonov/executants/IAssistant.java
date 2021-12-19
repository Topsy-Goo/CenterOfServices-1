package ru.gb.antonov.executants;

public interface IAssistant<T> {

    int QUEUE_INITIAL_CAPACITY = 11;

    T next ();

    boolean hasNext ();

    boolean queueRequest (T t);
}
