package ru.gb.antonov;

import ru.gb.antonov.dispatcher.Dispatcher;
import ru.gb.antonov.dispatcher.IReceptionist;
import ru.gb.antonov.doctypes.Sertificate;
import ru.gb.antonov.structs.*;
import ru.gb.antonov.doctypes.ISertificate;
import ru.gb.antonov.executant.Assistant;
import ru.gb.antonov.executant.SertificatExecutant;
import ru.gb.antonov.executant.IAssistant;
import ru.gb.antonov.executant.IExecutant;
import ru.gb.antonov.publisher.IPublisher;
import ru.gb.antonov.publisher.RequestPublisher;
import ru.gb.antonov.storage.IStorage;
import ru.gb.antonov.storage.SertificateStorage;

import java.util.Map;

public class Factory implements IFactory {

    public  static       IFactory instance;
    private static final Object   MONITOR = new Object();

    private Factory () {}

    public static IFactory getInstance () {
        if (instance == null) {
            synchronized (MONITOR) {
                if (instance == null) {
                    instance = new Factory();
                }
            }
        }
        return instance;
    }

    @Override public IPublisher<IRequest> getPublisher (Map<Causes, IAssistant<IRequest>> assistants) {
        return RequestPublisher.getInstance (assistants);
    }

    @Override public IReceptionist getReceptionist (IPublisher<IRequest> requestPublisher) {
        return Dispatcher.getInstance (requestPublisher);
    }

    @Override public IStorage<ISertificate> getSertificateStorage () {
        return SertificateStorage.getInstance();
    }

    @Override public IAssistant<IRequest> newAssistant (Causes cause) {
        return new Assistant (cause);
    }

    @Override public IExecutant<ISertificate, IRequest> newSerificateExecutant (
                        Causes cause, IAssistant<IRequest> assistant, IStorage<ISertificate> sertStorage)
    {
        return new SertificatExecutant (cause, assistant, sertStorage);
    }

    @Override public ISertificate createEmptySertificate () {
        return Sertificate.createEmpty();
    }

    @Override public IRequest newRequest (ICustomer customer, Integer priority) {
        return new Request (customer, priority);
    }
}
