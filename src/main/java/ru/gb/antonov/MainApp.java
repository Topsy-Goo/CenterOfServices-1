package ru.gb.antonov;

import ru.gb.antonov.dispatcher.IDispatcher;
import ru.gb.antonov.dispatcher.IReceptionist;
import ru.gb.antonov.doctypes.ICertificate;
import ru.gb.antonov.executants.IAssistant;
import ru.gb.antonov.executants.IExecutant;
import ru.gb.antonov.mvc.HttpPageServer;
import ru.gb.antonov.mvc.HttpRequestController;
import ru.gb.antonov.storage.IIdentityMap;
import ru.gb.antonov.storage.IStorage;
import ru.gb.antonov.structs.Causes;
import ru.gb.antonov.structs.CosOperations;
import ru.gb.antonov.structs.IRequest;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import static ru.gb.antonov.Factory.lnprint;

public class MainApp {

    public  static       IFactory<ICertificate>      factory;
    private static       IReceptionist<ICertificate> receptionist;
    private static final ConcurrentMap<Long, Object> results;
    private static final ConcurrentMap<CosOperations, IAssistant<IRequest>>        assistants;
    private static final ConcurrentMap<Causes, IExecutant<ICertificate, IRequest>> executants;
    private static       IDispatcher<ICertificate> dispatcher;
    private static       IIdentityMap              identityMap;
    private static       IStorage<ICertificate>    sertificateStorage;

    public  static final int    PAGE_SERVER_PORT = 5555;
    public  static final int    REQUEST_SERVER_PORT = 4444;
    public  static final String PAGE_DEFAULT     = "index.html"/**//*"main.html"*/;

    static {
        factory    = Factory.getInstance();
        results    = new ConcurrentHashMap<>();
        assistants = new ConcurrentHashMap<>();
        executants = new ConcurrentHashMap<>();
    }

    public static void main (String[] args) {

    //инициализация (очерёдность имеет значение):
        sertificateStorage = factory.getSingleStorage();
        identityMap = factory.getSingleIdentityMap();

        for (CosOperations op : CosOperations.values())
            assistants.put (op, factory.getAssistant (op));

        //Такое создание Executant'ов является упрощением. По идее, эти объекты не должны быть
        // одинаковыми. Они, как минмиум, должны по различную обрабатывать запросы.
        for (Causes c : Causes.values())
            executants.put (c, factory.getExecutant (c));

        dispatcher = factory.getSingleDispatcher();
        new Thread (dispatcher).start();
        factory.getSinglePublisher();
        receptionist = factory.getSingleReceptioniist();

    //работа и остановка приложения:
        simulateCustomersFlow();
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

/** имитируем поток клиентов */
    private static void simulateCustomersFlow () {
        lnprint ("--------------- Начало имитации потока клиентов. ---------------");
        try {
            new Thread (new HttpPageServer (PAGE_SERVER_PORT), "pageServer").start();
            HttpRequestController.go (REQUEST_SERVER_PORT);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            lnprint ("--------------- Конец имитации потока клиентов. ---------------");
        //Остановка приложения (в обратном порядке):
            receptionist.stop();
            dispatcher.stop();  //< по идее, нужно сперва дождаться окончания обработки всех сформированных запросов
            identityMap.stop(); //< этот вызов должен выполняться до остановки хранилища (проверяется кэшь)
            sertificateStorage.stop(); //< возможно, этот вызов должен делать dispatcher (или медиатор)
        }
    }
}

