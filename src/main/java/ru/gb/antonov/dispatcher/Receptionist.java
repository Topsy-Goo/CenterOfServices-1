package ru.gb.antonov.dispatcher;

import ru.gb.antonov.MainApp;
import ru.gb.antonov.doctypes.ICertificate;
import ru.gb.antonov.publisher.IPublisher;
import ru.gb.antonov.structs.ICustomer;
import ru.gb.antonov.structs.IRequest;

import java.util.Random;

import static ru.gb.antonov.Factory.lnprint;

public class Receptionist implements IReceptionist<ICertificate> {

    private static       Receptionist instance;
    private static final Object       MONITOR = new Object();
    private static       boolean busy = true;
    private final        IPublisher<IRequest> publisher;

    private Receptionist () {
        publisher  = MainApp.factory.getSinglePublisher();
        busy = false;
        lnprint ("Экземпляр Receptionist создан.");
    }

    @Override public void stop () {
        busy = true;
        lnprint ("Вызван Receptionist.stop().");
    }

    public static Receptionist getInstance () {
        if (instance == null)
            synchronized (MONITOR) {
                if (instance == null)
                    instance = new Receptionist();
            }
        return instance;
    }

    @Override public Long makeService (ICustomer customer) {
        if (busy)
            return null;
        int priority = calcPriority();
        IRequest request = MainApp.factory.newRequest (customer, priority);
        return publish (request) ? request.getId() : null;
    }

    @Override public Object resultByRequestId (Long requestId) {
        return busy ? null : MainApp.getResult (requestId);
    }

    private Integer calcPriority () { return new Random(47).nextInt(20); }

    private boolean publish (IRequest request) { return publisher.publish (request); }
}
