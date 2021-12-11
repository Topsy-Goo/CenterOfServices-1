package ru.gb.antonov.executant;

import ru.gb.antonov.MainApp;
import ru.gb.antonov.doctypes.ISertificate;
import ru.gb.antonov.storage.IStorage;
import ru.gb.antonov.structs.Causes;
import ru.gb.antonov.structs.IRequest;

public class SertificatExecutant implements IExecutant <ISertificate, IRequest> {

    protected       boolean doRun = false;
    private   final Causes  cause;
    private   final IAssistant<IRequest>   assistant;
    private   final IStorage<ISertificate> storage;

    public SertificatExecutant (Causes cause, IAssistant<IRequest> assistant, IStorage<ISertificate> storage) {
        this.cause     = cause;
        this.assistant = assistant;
        this.storage   = storage;
    }

    @Override public void run () {
        doRun = true;
        while (doRun)
        {
            if (assistant.hasNext()) {
                ISertificate sert = execute (assistant.next());
                if (sert != null)
                    storage.save (sert);
            }
        }
    }
//-------------- реализация шаблона builder для создания объекта ISertificate ------------

    @Override public ISertificate execute (IRequest request) {
        return (request == null) ? null /* : Sertificate.builder()
                                                  .withCustomerId (request.getCustomer().getId())
                                                  .withCause (cause)
                                                  .build();*/
                                 : MainApp.factory.createEmptySertificate()
                                                  .withCustomerId (request.getCustomer().getId())
                                                  .withCause (cause)
                                                  .build();
    }
}
