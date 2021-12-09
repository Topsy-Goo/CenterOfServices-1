package ru.gb.antonov.dispatcher;

import ru.gb.antonov.structs.Customer;
import ru.gb.antonov.structs.Request;
import ru.gb.antonov.structs.Sertificate;
import ru.gb.antonov.publisher.IPublisher;
import ru.gb.antonov.storage.IStorage;
import ru.gb.antonov.storage.StorageService;

import java.util.Collection;
import java.util.Random;

public class Dispatcher implements IReceptionist {

    private       static Dispatcher            instance;
    private final        IPublisher            publisher;
    private final        IStorage<Sertificate> storage;

    protected Dispatcher (IPublisher p) {
        publisher = p;
        instance = this;
        storage = StorageService.getInstance();
    }

    public static IReceptionist getInstance (IPublisher publisher) {
        if (instance == null)
            instance = new Dispatcher (publisher);
        return instance;
    }

    @Override public boolean receive (Customer customer) {
        int priority = calcPriority();
        Request request = new Request (customer.getId(), customer.getCause(), priority);
        return publisher.publish (request);
    }

    protected int calcPriority () { return new Random(47).nextInt(20); }

    @Override public Collection<Sertificate> getSertificatesByCause (Customer customer) {
        return storage.findAllByCustomerIdAndCause(customer.getId(), customer.getCause());
    }

    @Override public Collection<Sertificate> getSertificates (Customer customer) {
        return storage.findAllByCustomerId(customer.getId());
    }
}
