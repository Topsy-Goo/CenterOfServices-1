package ru.gb.antonov.executant;

import ru.gb.antonov.structs.Causes;
import ru.gb.antonov.structs.Request;
import ru.gb.antonov.structs.Sertificate;
import ru.gb.antonov.storage.IStorage;

public class Executant implements IExecutant, Runnable {

    protected       boolean               doRun = false;
    private   final Causes                cause;
    private   final IAssistant<Request>   assistant;
    private   final IStorage<Sertificate> storage;

    public Executant (Causes cause, IAssistant<Request> assistant, IStorage<Sertificate> storage) {
        this.cause     = cause;
        this.assistant = assistant;
        this.storage   = storage;
    }

    @Override
    public void run () {
        doRun = true;
        while (doRun)
        {
            if (assistant.hasNext()) {
                Request request = assistant.next();
                if (request != null)
                    storage.save (new Sertificate (request.getCustomerId(), cause));
            }
        }
    }
}
