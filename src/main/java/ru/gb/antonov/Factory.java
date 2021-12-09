package ru.gb.antonov;

import ru.gb.antonov.dispatcher.Dispatcher;
import ru.gb.antonov.dispatcher.IReceptionist;
import ru.gb.antonov.structs.Causes;
import ru.gb.antonov.structs.Request;
import ru.gb.antonov.structs.Sertificate;
import ru.gb.antonov.executant.Assistant;
import ru.gb.antonov.executant.Executant;
import ru.gb.antonov.executant.IAssistant;
import ru.gb.antonov.executant.IExecutant;
import ru.gb.antonov.publisher.IPublisher;
import ru.gb.antonov.publisher.Publisher;
import ru.gb.antonov.storage.IStorage;
import ru.gb.antonov.storage.StorageService;

import java.util.Map;

public class Factory {

    public static IPublisher getPublisher (Map<Causes, IAssistant<Request>> assistants) {
        return Publisher.getInstance (assistants);
    }

    public static IReceptionist getReceptionist (IPublisher publisher) {
        return Dispatcher.getInstance (publisher);
    }

    public static IExecutant newExecutant (Causes cause, IAssistant<Request> assistant,
                                          IStorage<Sertificate> storage)
    {
        return new Executant(cause, assistant, storage);
    }

    public static IAssistant<Request> newAssistant (Causes cause) {
        return new Assistant (cause);
    }

    public static IStorage<Sertificate> getSertificateStorage () {
        return StorageService.getInstance();
    }
}
