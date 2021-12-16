package ru.gb.antonov.executants;

public interface IAssistant<T> {

    T next ();

    boolean hasNext ();

    boolean queueRequest (T t);
}
