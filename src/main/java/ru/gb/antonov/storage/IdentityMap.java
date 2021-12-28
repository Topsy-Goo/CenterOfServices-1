package ru.gb.antonov.storage;

import ru.gb.antonov.MainApp;
import ru.gb.antonov.doctypes.ICertificate;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import static ru.gb.antonov.Factory.lnprint;

public class IdentityMap implements IIdentityMap {

    private static IdentityMap instance;
    private static final Object MONITOR = new Object();
    private static final ConcurrentMap<Long, ICertificate> certificatesCache = new ConcurrentHashMap<>();
    IStorage<ICertificate> sertificateStorage;

    private IdentityMap () {
        sertificateStorage = MainApp.factory.getSingleStorage();
        lnprint ("Экземпляр IdentityMap создан.");
    }

    public static IIdentityMap getInstance () {
        if (instance == null)
            synchronized (MONITOR) {
                if (instance == null)
                    instance = new IdentityMap();
            }
        return instance;
    }

    @Override public void stop () {
        /*  Проверяем кэш на наличие несохранённых данных и, при необходимости, сбрасываем их в хранилище.
        Возможно, для этого придётся сделать внутренний класс, в котором будут поля ICertificate и boolean
        Changed.
        */
        lnprint ("Вызван IdentityMap.stop().");
    }

/** Сохраняет сертификат в хранилище и удалёет его из кэша. */
    @Override public ICertificate save (ICertificate certificate) throws IOException {
        ICertificate result = null;
        try {
            result = sertificateStorage.save (certificate);
        }
        catch (SQLException sqle) { throw new IOException (sqle); }

        if (result != null)
            certificatesCache.remove (certificate.getId());
        return result;
    }

/** Из хранилища выбирает сертификаты с указанным {@code custome_id} и помещает их в кэш, если их нет в
кэше. Считает, что в кэше находится более актуальная версия сертификата. */
    @Override public Collection<ICertificate> findAllByCustomerId (Long cid) throws IOException {
        Collection<ICertificate> result = null;
        try {
            result = sertificateStorage.findAllByCustomerId (cid);
            if (result != null)
                for (ICertificate c : result)
                    certificatesCache.putIfAbsent (c.getId(), c);
        }
        catch (SQLException sqle) { throw new IOException (sqle); }
        return result;
    }
}
