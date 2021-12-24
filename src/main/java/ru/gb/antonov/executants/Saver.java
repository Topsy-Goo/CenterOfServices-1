package ru.gb.antonov.executants;

import ru.gb.antonov.MainApp;
import ru.gb.antonov.doctypes.ICertificate;
import ru.gb.antonov.structs.IRequest;

import java.io.IOException;

/** Изготавливаем сертификат и сохраняем его в хранилище. Заказчику сейчас сертификат не отдаём, —
он не просил это делать.<p>
Сохранение сертификата — обязательный процесс, т.к. именно при сохранении сертификату присваивается ID.
Без ID сертификат недействителен.
*/
public class Saver extends OperationHandler<ICertificate, Boolean, IRequest> {

    public Saver (IExecutant<ICertificate, IRequest> e) {
        super(e);
    }

    @Override public Boolean handle (IRequest request) {
        ICertificate certificate = executant.execute (request);
        try {
            certificate = MainApp.factory.getSingleIdentityMap().save (certificate);
        }
        catch (IOException e) {
            System.out.printf ("Saver: Не удалось сохранить сертификат по запросу %d.", request.getId());
            certificate = null; //< несохранённый сертификат не имеет ID и т.о. является недействительным
        }
        return certificate != null;
    }
}
