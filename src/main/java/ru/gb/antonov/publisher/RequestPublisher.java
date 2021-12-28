package ru.gb.antonov.publisher;

import ru.gb.antonov.MainApp;
import ru.gb.antonov.executants.IAssistant;
import ru.gb.antonov.structs.IRequest;

import static ru.gb.antonov.Factory.lnprint;

public class RequestPublisher implements IPublisher<IRequest> {

    private static       IPublisher<IRequest> instance;
    private static final Object               MONITOR = new Object();

    private RequestPublisher () {
        lnprint ("Экземпляр RequestPublisher создан.");
    }

    public static IPublisher<IRequest> getInstance () {
        if (instance == null)
            synchronized (MONITOR) {
                if (instance == null)
                    instance = new RequestPublisher();
            }
        return instance;
    }

    @Override public boolean publish (IRequest request) {
        IAssistant<IRequest> assistant = MainApp.assistantFor (request.getCustomer().getOperation());
        if (assistant == null)
            throw new RuntimeException();
        return assistant.queueRequest (request);
    }
}
