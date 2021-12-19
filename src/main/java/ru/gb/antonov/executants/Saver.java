package ru.gb.antonov.executants;

import ru.gb.antonov.MainApp;
import ru.gb.antonov.doctypes.ICertificate;
import ru.gb.antonov.structs.IRequest;

/** Изготавливаем сертификат и сохраняем его в IStorage.
*/
public class Saver extends OperationHandler<ICertificate, Boolean, IRequest> {

    public Saver (IExecutant<ICertificate, IRequest> e) {
        super(e);
    }

    @Override public Boolean handle (IRequest request) {
        ICertificate certificate = executant.execute (request);
        return null != MainApp.factory.getSingleStorage().save (certificate);
    }
}
