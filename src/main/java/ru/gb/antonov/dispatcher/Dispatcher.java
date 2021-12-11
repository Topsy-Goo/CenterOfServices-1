package ru.gb.antonov.dispatcher;

import ru.gb.antonov.MainApp;
import ru.gb.antonov.doctypes.ISertificate;
import ru.gb.antonov.publisher.IPublisher;
import ru.gb.antonov.storage.IStorage;
import ru.gb.antonov.structs.ICustomer;
import ru.gb.antonov.structs.IRequest;

import java.util.Collection;
import java.util.Random;

public class Dispatcher implements IReceptionist {

    private       static Dispatcher instance;
    private final static Object     MONITOR = new Object();
    private final        IPublisher<IRequest>   publisher;
    private final        IStorage<ISertificate> storage;

    protected Dispatcher (IPublisher<IRequest> p) {
        publisher = p;
        storage = MainApp.factory.getSertificateStorage();
    }

    public static IReceptionist getInstance (IPublisher<IRequest> publisher) {
        if (instance == null) {
            synchronized (MONITOR) {
                if (instance == null) {
                    instance = new Dispatcher (publisher);
                }
            }
        }
        return instance;
    }

    @Override public boolean receive (ICustomer customer) {
        int priority = calcPriority();
        IRequest request = MainApp.factory.newRequest (customer, priority);
        return publisher.publish (request);
    }

    protected int calcPriority () { return new Random(47).nextInt(20); }

    @Override public Collection<ISertificate> getSertificatesByCause (ICustomer customer) {
        return storage.findAllByCustomerIdAndCause(customer.getId(), customer.getCause());
    }

    @Override public Collection<ISertificate> getSertificates (ICustomer customer) {
        return storage.findAllByCustomerId(customer.getId());
    }
}
