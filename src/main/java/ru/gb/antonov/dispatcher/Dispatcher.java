package ru.gb.antonov.dispatcher;

import ru.gb.antonov.MainApp;
import ru.gb.antonov.doctypes.ICertificate;
import ru.gb.antonov.executants.*;
import ru.gb.antonov.structs.CosOperations;
import ru.gb.antonov.structs.IRequest;

import static ru.gb.antonov.Factory.lnprint;

public class Dispatcher extends AbsDispatcher<ICertificate, IRequest>
                        implements IDispatcher<ICertificate>
{
    private static       Dispatcher instance;
    private final static Object     MONITOR = new Object();
    private              boolean    doRun;

    protected Dispatcher () {
        lnprint ("Экземпляр Dispatcher создан.");
    }

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
                    workUpRequest (assistant.next());
            }
        }
        lnprint ("Dispatcher.run() завершился.");
    }

    @Override public void stop () {
        doRun = false;
        lnprint ("Вызван Dispatcher.stop().");
    }

//----------------- реализация шаблона Шаблонный метод -------------------------------------

    @Override protected OperationHandler<ICertificate, ?, IRequest> getOperationHandler (IRequest request) {

        IExecutant<ICertificate, IRequest> executant =
                        MainApp.executantFor (request.getCustomer().getCause());

        switch (request.getCustomer().getOperation()) {
            case MAKE:  return new Maker (executant);
            case SAVE:  return new Saver (executant);
            case PRINT: return new Printer (executant);
            default: throw new RuntimeException ("Unknown type of CosOperations");
        }
    }

    @Override protected Object getKey (IRequest request) { return request.getId(); }

    @Override protected void putResult (Object key, Object result) {
        MainApp.putResult ((Long) key, result);
    }
}
