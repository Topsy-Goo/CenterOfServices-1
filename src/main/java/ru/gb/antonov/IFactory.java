package ru.gb.antonov;

import ru.gb.antonov.dispatcher.IReceptionist;
import ru.gb.antonov.doctypes.ISertificate;
import ru.gb.antonov.executant.IAssistant;
import ru.gb.antonov.executant.IExecutant;
import ru.gb.antonov.publisher.IPublisher;
import ru.gb.antonov.storage.IStorage;
import ru.gb.antonov.structs.Causes;
import ru.gb.antonov.structs.ICustomer;
import ru.gb.antonov.structs.IRequest;

import java.util.Map;

public interface IFactory {

    IStorage<ISertificate> getSertificateStorage ();

    ISertificate createEmptySertificate ();

    IPublisher<IRequest> getPublisher (Map<Causes, IAssistant<IRequest>> assistants);

    IReceptionist getReceptionist (IPublisher<IRequest> requestPublisher);

    IAssistant<IRequest> newAssistant (Causes cause);

    IExecutant<ISertificate, IRequest> newSerificateExecutant (
            Causes cause,
            IAssistant<IRequest> assistant,
            IStorage<ISertificate> sertStorage);

    IRequest newRequest (ICustomer customer, Integer priority);
}
