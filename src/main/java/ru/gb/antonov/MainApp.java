package ru.gb.antonov;

import ru.gb.antonov.dispatcher.IDispatcher;
import ru.gb.antonov.dispatcher.IReceptionist;
import ru.gb.antonov.doctypes.ICertificate;
import ru.gb.antonov.executants.IAssistant;
import ru.gb.antonov.executants.IExecutant;
import ru.gb.antonov.storage.IStorage;
import ru.gb.antonov.structs.Causes;
import ru.gb.antonov.structs.CosOperations;
import ru.gb.antonov.structs.IRequest;

import java.util.HashMap;
import java.util.Map;

public class MainApp {

    public  static IFactory<ICertificate>      factory = Factory.getInstance();
    private static IStorage<ICertificate>      sertificateStorage;
    private static IReceptionist<ICertificate> receptionist;
    private static final Map<Long, Object>     results = new HashMap<>();
    private static final Map<CosOperations, IAssistant<IRequest>>        assistants = new HashMap<>();
    private static final Map<Causes, IExecutant<ICertificate, IRequest>> executants = new HashMap<>();

    public static void main (String[] args) {

        for (CosOperations op : CosOperations.values())
            assistants.put (op, factory.newAssistant());

        //Такое создание Executant'ов является упрощением. По идее, эти объекты не должны быть
        // одинаковыми. Они, как минмиум, должны по различную обрабатывать запросы.
        for (Causes c : Causes.values())
            executants.put (c, factory.newExecutant());

        IDispatcher<ICertificate> dispatcher = factory.getSingleDispatcher();
        dispatcher.run();
        receptionist = factory.getSingleReceptioniist();

        simulateCustomersFlow();
        dispatcher.stop();
    }

    public static IAssistant<IRequest> assistantFor (CosOperations operation) {
        return assistants.get (operation);
    }

    public static IExecutant<ICertificate, IRequest> executantFor (Causes cause) {
        return executants.get (cause);
    }

    public static IReceptionist<ICertificate> getReceptionist () { return receptionist; }

    public static void putResult (Long requestId, Object result) { results.put (requestId, result); }

    public static Object getResult (Long requestId) { return results.get (requestId); }

    private static void simulateCustomersFlow () { /* имитируем поток клиентов */ }
}

