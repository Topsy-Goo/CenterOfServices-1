package ru.gb.antonov;

import ru.gb.antonov.dispatcher.*;
import ru.gb.antonov.doctypes.ICertificate;
import ru.gb.antonov.executants.Assistant;
import ru.gb.antonov.executants.CertificatExecutant;
import ru.gb.antonov.executants.IAssistant;
import ru.gb.antonov.executants.IExecutant;
import ru.gb.antonov.publisher.IPublisher;
import ru.gb.antonov.publisher.RequestPublisher;
import ru.gb.antonov.storage.IIdentityMap;
import ru.gb.antonov.storage.IStorage;
import ru.gb.antonov.storage.CertificateStorage;
import ru.gb.antonov.storage.IdentityMap;
import ru.gb.antonov.structs.*;

public class Factory implements IFactory<ICertificate> {

    public  static       IFactory<ICertificate> instance;
    private static final Object   MONITOR = new Object();

    private Factory () {}

    public static IFactory<ICertificate> getInstance () {
        if (instance == null)
            synchronized (MONITOR) {
                if (instance == null)
                    instance = new Factory();
            }
        return instance;
    }

    @Override public IPublisher<IRequest> getSinglePublisher () {   //disp
        return RequestPublisher.getInstance();
    }

    @Override public IDispatcher<ICertificate> getSingleDispatcher () {   //main
        return Dispatcher.getInstance();
    }

    @Override public IReceptionist<ICertificate> getSingleReceptioniist () {    //main
        return Receptionist.getInstance();
    }

    @Override public IStorage<ICertificate> getSingleStorage () {    //main, disp
        return CertificateStorage.getInstance();
    }

    @Override public IAssistant<IRequest> getAssistant () {     //main
        return new Assistant ();
    }

    @Override public IExecutant<ICertificate, IRequest> getExecutant () {   //main
        return new CertificatExecutant();
    }

    @Override public IRequest newRequest (ICustomer customer, Integer priority) {   //recept
        return new Request (customer, priority);
    }

    @Override public IIdentityMap getSingleIdentityMap () {
        return IdentityMap.getInstance();
    }
}
