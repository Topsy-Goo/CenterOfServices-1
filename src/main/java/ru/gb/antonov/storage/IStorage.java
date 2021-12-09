package ru.gb.antonov.storage;

import ru.gb.antonov.structs.Causes;

import java.util.Collection;

public interface IStorage <S> {

    S save (S s);
    Collection<S> findAllByCustomerIdAndCause (Long cid, Causes cause);
    Collection<S> findAllByCustomerId (Long cid);
}
