package ru.gb.antonov.dispatcher;

import ru.gb.antonov.Stopable;
import ru.gb.antonov.structs.ICustomer;

public interface IReceptionist<T> extends Stopable {

    Long makeService (ICustomer customer);

    Object resultByRequestId (Long requestId);
}
