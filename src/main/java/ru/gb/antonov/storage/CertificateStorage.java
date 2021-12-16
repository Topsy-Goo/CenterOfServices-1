package ru.gb.antonov.storage;

import ru.gb.antonov.doctypes.ICertificate;
import ru.gb.antonov.structs.Causes;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

public class CertificateStorage implements IStorage<ICertificate> {

    private static       CertificateStorage       instance;
    private static final Object                   MONITOR = new Object();
    private static       Collection<ICertificate> certificates;

    private CertificateStorage () {
        certificates = new ArrayList<>();
    }

    public static IStorage<ICertificate> getInstance () {
        if (instance == null) {
            synchronized (MONITOR) {
                if (instance == null) {
                    instance = new CertificateStorage();
                }
            }
        }
        return instance;
    }

    @Override public ICertificate save (ICertificate certificate) {
        return certificates.add (certificate) ? certificate : null;
    }

    @Override public Collection<ICertificate> findAllByCustomerId (Long cid) {
        return certificates.stream()
                           .filter (s->s.getCustomerId().equals(cid))
                           .collect (Collectors.toList());
    }
}
