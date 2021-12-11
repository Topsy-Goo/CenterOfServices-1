package ru.gb.antonov.publisher;

public interface IPublisher<T> {

    boolean publish (T t);
}
