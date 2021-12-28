package ru.gb.antonov.storage;

import ru.gb.antonov.Stopable;
import ru.gb.antonov.doctypes.ICertificate;

import java.io.IOException;
import java.util.Collection;

public interface IIdentityMap extends Stopable {

    ICertificate save (ICertificate certificate) throws IOException;

    Collection<ICertificate> findAllByCustomerId (Long cid) throws IOException;
}
