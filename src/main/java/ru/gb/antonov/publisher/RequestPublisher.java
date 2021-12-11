package ru.gb.antonov.publisher;

import ru.gb.antonov.executant.IAssistant;
import ru.gb.antonov.structs.Causes;
import ru.gb.antonov.structs.IRequest;

import java.util.Map;

public class RequestPublisher implements IPublisher<IRequest> {

    private static       IPublisher<IRequest> instance;
    private static final Object               MONITOR = new Object();
    private        final Map<Causes, IAssistant<IRequest>> assistants;

    private RequestPublisher (Map<Causes, IAssistant<IRequest>> assistants) {
        this.assistants = assistants;
    }

    public static IPublisher<IRequest> getInstance (Map<Causes, IAssistant<IRequest>> assistants) {
        if (instance == null) {
            synchronized (MONITOR) {
                if (instance == null) {
                    instance = new RequestPublisher (assistants);
                }
            }
        }
        return instance;
    }

    @Override public boolean publish (IRequest request) {

        IAssistant<IRequest> assistant = assistants.get (request.getCustomer().getCause());
        if (assistant == null)
            throw new RuntimeException();
        return assistant.queueRequest(request);
    }
}
