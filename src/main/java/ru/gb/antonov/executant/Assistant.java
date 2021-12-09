package ru.gb.antonov.executant;

import ru.gb.antonov.structs.Causes;
import ru.gb.antonov.structs.Request;

import java.util.Comparator;
import java.util.PriorityQueue;

public class Assistant implements IAssistant<Request>  {

    private final  Causes                 cause;
    private final  PriorityQueue<Request> requests;

    public Assistant (Causes cause) {
        this.cause = cause;
        requests = new PriorityQueue<>(Comparator.comparingInt(Request::getPriority));
    }

    @Override public boolean hasNext () { return !requests.isEmpty(); }

    @Override public Request next ()    { return requests.poll(); }

    @Override public boolean queueRequest (Request request) { return requests.offer (request); }
}
