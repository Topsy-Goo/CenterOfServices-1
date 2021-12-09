package ru.gb.antonov;

import ru.gb.antonov.dispatcher.IReceptionist;
import ru.gb.antonov.structs.Causes;
import ru.gb.antonov.structs.Request;
import ru.gb.antonov.structs.Sertificate;
import ru.gb.antonov.executant.IAssistant;
import ru.gb.antonov.executant.IExecutant;
import ru.gb.antonov.publisher.IPublisher;
import ru.gb.antonov.storage.IStorage;

import java.util.HashMap;
import java.util.Map;

public class MainApp {

    private static final Map<Causes, IAssistant<Request>> assistants = new HashMap<>();

    public static void main (String[] args) {

        MainApp master = new MainApp();
        IStorage<Sertificate> requestIStorage = Factory.getSertificateStorage();

        Causes[] causes = Causes.values();
        for (Causes c : causes) {
            IExecutant executant = master.createExecutantFor (c, requestIStorage);
            new Thread ((Runnable) executant);
        }
        IPublisher    publisher    = Factory.getPublisher (assistants);
        IReceptionist receptionist = Factory.getReceptionist (publisher);

        simulateCustomersFlow();
    }

    private IExecutant createExecutantFor (Causes cause, IStorage<Sertificate> storage) {

        IAssistant<Request> assistant = assistants.get(cause);
        if (assistant == null) {
            assistant = Factory.newAssistant (cause);
            assistants.put (cause, assistant);
        }
        return Factory.newExecutant (cause, assistant, storage);
    }

    private static void simulateCustomersFlow () {
        //эмитируем поток клиентов
    }
}
