package ru.gb.antonov.executants;

import ru.gb.antonov.doctypes.ICertificate;
import ru.gb.antonov.structs.IRequest;

/** Изготавливаем сертификат и отдаём его заказчику.
*/
public class Maker extends Handler <ICertificate, ICertificate, IRequest> {

    public Maker (IExecutant<ICertificate, IRequest> e) {
        super(e);
    }

    @Override public ICertificate handle (IRequest request) {
        return executant.execute(request);
    }
}
