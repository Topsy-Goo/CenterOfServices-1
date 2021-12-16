package ru.gb.antonov.dispatcher;

import ru.gb.antonov.MainApp;
import ru.gb.antonov.doctypes.ICertificate;
import ru.gb.antonov.executants.*;
import ru.gb.antonov.structs.CosOperations;
import ru.gb.antonov.structs.IRequest;

public class Dispatcher implements IDispatcher<ICertificate> {

    private static       Dispatcher instance;
    private final static Object     MONITOR    = new Object();
    private              boolean    doRun;

    protected Dispatcher () {}

    public static IDispatcher<ICertificate> getInstance () {
        if (instance == null)
            synchronized (MONITOR) {
                if (instance == null)
                    instance = new Dispatcher();
            }
        return instance;
    }

    @Override public void run () {
        doRun = true;
        while (doRun) {
            for (CosOperations operation : CosOperations.values()) {
                IAssistant<IRequest> assistant = MainApp.assistantFor (operation);
                if (assistant.hasNext())
                    workUpRequest (assistant);
            }
        }
    }

    private void workUpRequest (IAssistant<IRequest> assistant) {

        IRequest request = assistant.next();
        Object result = null;
        IExecutant<ICertificate, IRequest> executant = MainApp.executantFor (request.getCustomer().getCause());

        switch (request.getCustomer().getOperation()) {
            case MAKE:  result = new Maker (executant).handle (request);
                break;
            case SAVE:  result = new Saver (executant).handle (request);
                break;
            case PRINT: result = new Printer (executant).handle (request);
                break;
            default: throw new RuntimeException ("Unknown type of CosOperations");
        }
        MainApp.putResult (request.getId(), result);
    }

    @Override public void stop () { doRun = false; }
}
