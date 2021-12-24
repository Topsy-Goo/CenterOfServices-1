package ru.gb.antonov.executants;

import ru.gb.antonov.MainApp;
import ru.gb.antonov.doctypes.ICertificate;
import ru.gb.antonov.structs.IRequest;

import java.io.IOException;

/** Изготавливаем сертификат, сохраняем его в хранилище и отдаём его заказчику.<p>
Сохранение сертификата — обязательный процесс, т.к. именно при сохранении сертификату присваивается ID.
Без ID сертификат недействителен.
*/
public class Maker extends OperationHandler<ICertificate, ICertificate, IRequest> {

    public Maker (IExecutant<ICertificate, IRequest> e) {
        super(e);
    }

    @Override public ICertificate handle (IRequest request) {
        ICertificate certificate = executant.execute (request);
        try {
            certificate = MainApp.factory.getSingleIdentityMap().save (certificate);
        }
        catch (IOException e) {
            System.out.printf ("Maker: Не удалось сохранить сертификат по запросу %d.", request.getId());
            return null; //< несохранённый сертификат не имеет ID и т.о. является недействительным
        }
        return certificate;
    }
}
