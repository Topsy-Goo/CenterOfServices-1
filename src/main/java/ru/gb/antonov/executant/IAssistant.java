package ru.gb.antonov.executant;

public interface IAssistant<T> {

    T       next ();
    boolean hasNext ();
    boolean queueRequest (T t);
}
