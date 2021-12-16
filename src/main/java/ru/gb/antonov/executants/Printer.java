package ru.gb.antonov.executants;

import ru.gb.antonov.doctypes.ICertificate;
import ru.gb.antonov.structs.IRequest;

/** Изготавливаем сертификат и «распечатываем» его в String.
*/
public class Printer extends Handler <ICertificate, String, IRequest> {

    public Printer (IExecutant<ICertificate, IRequest> e) {
        super(e);
    }

    @Override public String handle (IRequest request) {
        ICertificate certificate = executant.execute (request);
        return certificate.toString();
    }
}
