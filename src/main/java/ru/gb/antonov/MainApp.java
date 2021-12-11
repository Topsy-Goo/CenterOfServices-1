package ru.gb.antonov;

import ru.gb.antonov.dispatcher.IReceptionist;
import ru.gb.antonov.doctypes.ISertificate;
import ru.gb.antonov.executant.IAssistant;
import ru.gb.antonov.executant.IExecutant;
import ru.gb.antonov.publisher.IPublisher;
import ru.gb.antonov.storage.IStorage;
import ru.gb.antonov.structs.Causes;
import ru.gb.antonov.structs.IRequest;

import java.util.HashMap;
import java.util.Map;

public class MainApp {

    private static final Map<Causes, IAssistant<IRequest>> assistants = new HashMap<>();
    public  static       IFactory factory = Factory.getInstance();

    public static void main (String[] args) {

        MainApp master = new MainApp();
        IStorage<ISertificate> sertificateStorage = factory.getSertificateStorage();

        for (Causes cause : Causes.values()) {
            IExecutant<ISertificate, IRequest> executant = master.createExecutantFor (cause, sertificateStorage);
            new Thread (executant);
        }
        IPublisher<IRequest> requestIPublisher = factory.getPublisher (assistants);
        IReceptionist receptionist = factory.getReceptionist (requestIPublisher);

        simulateCustomersFlow();
    }

    private IExecutant<ISertificate, IRequest> createExecutantFor (Causes cause, IStorage<ISertificate> storage) {

        IAssistant<IRequest> assistant = assistants.get(cause);
        if (assistant == null) {
            assistant = factory.newAssistant (cause);
            assistants.put (cause, assistant);
        }
        return factory.newSerificateExecutant (cause, assistant, storage);
    }

    private static void simulateCustomersFlow () {
        //эмитируем поток клиентов
    }
}
