package ru.gb.antonov.storage;

import ru.gb.antonov.structs.Causes;
import ru.gb.antonov.structs.Sertificate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

public class StorageService implements IStorage<Sertificate> {

    private static StorageService instance;
    private static Collection<Sertificate> sertificates;

    private StorageService () {
        instance = this;
        sertificates = new ArrayList<>();
    }

    public static IStorage<Sertificate> getInstance () {
        if (instance == null)
            instance = new StorageService();
        return instance;
    }

    @Override public Sertificate save (Sertificate sertificate) {
        return sertificates.add (sertificate) ? sertificate : null;
    }

    @Override public Collection<Sertificate> findAllByCustomerIdAndCause (Long cid, Causes cause) {
        return sertificates.stream()
                           .filter(s->s.getCustomerId().equals(cid) && s.getCause().equals(cause))
                           .collect(Collectors.toList());
    }

    @Override public Collection<Sertificate> findAllByCustomerId (Long cid) {
        return sertificates.stream()
                           .filter(s->s.getCustomerId().equals(cid))
                           .collect(Collectors.toList());
    }
}
