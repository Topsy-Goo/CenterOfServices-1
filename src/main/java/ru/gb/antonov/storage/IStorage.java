package ru.gb.antonov.storage;

import ru.gb.antonov.Stopable;
import ru.gb.antonov.doctypes.ICertificate;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;

public interface IStorage <T> extends Stopable {

    T save (T t) throws SQLException, IOException;

    Collection<ICertificate> findAllByCustomerId (Long cid) throws SQLException;

    void stop ();
}
