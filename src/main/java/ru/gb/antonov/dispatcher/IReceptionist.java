package ru.gb.antonov.dispatcher;

import ru.gb.antonov.structs.ICustomer;

public interface IReceptionist<T> {

    Long makeService (ICustomer customer);

    Object resultByRequestId (Long requestId);
}
