package ru.gb.antonov.publisher;

import ru.gb.antonov.structs.Causes;
import ru.gb.antonov.structs.Request;
import ru.gb.antonov.executant.IAssistant;

import java.util.Map;

public class Publisher implements IPublisher {

    private static       Publisher                        instance;
    private        final Map<Causes, IAssistant<Request>> assistants;

    private Publisher (Map<Causes, IAssistant<Request>> assistants) {
        instance = this;
        this.assistants = assistants;
    }

    public static IPublisher getInstance (Map<Causes, IAssistant<Request>> assistants) {
        if (instance == null)
            new Publisher (assistants);
        return instance;
    }

    @Override public boolean publish (Request request) {

        IAssistant<Request> assistant = assistants.get(request.getCause());
        if (assistant == null)
            throw new RuntimeException();
        return assistant.queueRequest(request);
    }
}
