package ru.gb.antonov.dispatcher;

import ru.gb.antonov.MainApp;
import ru.gb.antonov.doctypes.ICertificate;
import ru.gb.antonov.publisher.IPublisher;
import ru.gb.antonov.structs.ICustomer;
import ru.gb.antonov.structs.IRequest;

import java.util.Random;

public class Receptionist implements IReceptionist<ICertificate> {

    private static       Receptionist         instance;
    private static final Object               MONITOR = new Object();
    private final        IPublisher<IRequest> publisher;

    private Receptionist () {
        publisher  = MainApp.factory.getSinglePublisher();
    }

    public static Receptionist getInstance ()
    {
        if (instance == null)
            synchronized (MONITOR) {
                if (instance == null)
                    instance = new Receptionist();
            }
        return instance;
    }

    @Override public Long makeService (ICustomer customer) {
        int priority = calcPriority();
        IRequest request = MainApp.factory.newRequest (customer, priority);
        return publish (request) ? request.getId() : null;
    }

    @Override public Object resultByRequestId (Long requestId) {
        return MainApp.getResult (requestId);
    }

    private Integer calcPriority () { return new Random(47).nextInt(20); }

    private boolean publish (IRequest request) { return publisher.publish (request); }
}
