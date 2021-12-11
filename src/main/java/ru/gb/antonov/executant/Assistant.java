package ru.gb.antonov.executant;

import ru.gb.antonov.structs.Causes;
import ru.gb.antonov.structs.IRequest;

import java.util.Comparator;
import java.util.PriorityQueue;

public class Assistant implements IAssistant<IRequest>  {

    private final  Causes cause;
    private final  PriorityQueue<IRequest> requests;

    public Assistant (Causes cause) {
        this.cause = cause;
        requests = new PriorityQueue<>(Comparator.comparingInt(IRequest::getPriority));
    }

    @Override public boolean hasNext () { return !requests.isEmpty(); }

    @Override public IRequest next ()   { return requests.poll(); }

    @Override public boolean queueRequest (IRequest request) { return requests.offer (request); }
}
