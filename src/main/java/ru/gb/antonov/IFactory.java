package ru.gb.antonov;

import ru.gb.antonov.dispatcher.IDispatcher;
import ru.gb.antonov.dispatcher.IReceptionist;
import ru.gb.antonov.executants.IAssistant;
import ru.gb.antonov.executants.IExecutant;
import ru.gb.antonov.publisher.IPublisher;
import ru.gb.antonov.storage.IIdentityMap;
import ru.gb.antonov.storage.IStorage;
import ru.gb.antonov.structs.Causes;
import ru.gb.antonov.structs.CosOperations;
import ru.gb.antonov.structs.ICustomer;
import ru.gb.antonov.structs.IRequest;

public interface IFactory<T> {

    IStorage<T> getSingleStorage ();

    IPublisher<IRequest> getSinglePublisher ();

    IDispatcher<T> getSingleDispatcher ();

    IReceptionist<T> getSingleReceptioniist ();

    IAssistant<IRequest> getAssistant (CosOperations op);

    IExecutant<T, IRequest> getExecutant (Causes cause);

    IRequest newRequest (ICustomer customer, Integer priority);

    IIdentityMap getSingleIdentityMap ();
}
