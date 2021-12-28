package ru.gb.antonov.executants;

import ru.gb.antonov.structs.CosOperations;
import ru.gb.antonov.structs.IRequest;

import java.util.Comparator;
import java.util.concurrent.PriorityBlockingQueue;

import static ru.gb.antonov.Factory.lnprint;

public class Assistant implements IAssistant<IRequest>  {

    private final PriorityBlockingQueue<IRequest> requests;

    public Assistant (CosOperations op) {
        this.requests = new PriorityBlockingQueue<>(
                QUEUE_INITIAL_CAPACITY,
                Comparator.comparingInt (IRequest::getPriority));
        lnprint ("Экземпляр Assistant ("+ op.name() +") создан.");
    }

    @Override public boolean hasNext () { return !requests.isEmpty(); }

    @Override public IRequest next ()   { return requests.poll(); }

    @Override public boolean queueRequest (IRequest request) { return requests.offer (request); }
}
