package ru.gb.antonov.storage;

import java.util.Collection;

public interface IStorage <T> {

    T save (T t);

    Collection<T> findAllByCustomerId (Long cid);
}
