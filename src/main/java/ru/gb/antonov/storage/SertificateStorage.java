package ru.gb.antonov.storage;

import ru.gb.antonov.doctypes.ISertificate;
import ru.gb.antonov.structs.Causes;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

public class SertificateStorage implements IStorage<ISertificate> {

    private static       SertificateStorage instance;
    private static final Object             MONITOR = new Object();
    private static       Collection<ISertificate> sertificates;

    private SertificateStorage () {
        sertificates = new ArrayList<>();
    }

    public static IStorage<ISertificate> getInstance () {
        if (instance == null) {
            synchronized (MONITOR) {
                if (instance == null) {
                    instance = new SertificateStorage();
                }
            }
        }
        return instance;
    }

    @Override public ISertificate save (ISertificate sertificate) {
        return sertificates.add (sertificate) ? sertificate : null;
    }

    @Override public Collection<ISertificate> findAllByCustomerIdAndCause (Long cid, Causes cause) {
        return sertificates.stream()
                           .filter(s->s.getCustomerId().equals(cid) && s.getCause().equals(cause))
                           .collect(Collectors.toList());
    }

    @Override public Collection<ISertificate> findAllByCustomerId (Long cid) {
        return sertificates.stream()
                           .filter(s->s.getCustomerId().equals(cid))
                           .collect(Collectors.toList());
    }
}
