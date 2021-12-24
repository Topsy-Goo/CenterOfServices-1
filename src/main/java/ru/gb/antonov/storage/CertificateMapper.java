package ru.gb.antonov.storage;

import ru.gb.antonov.doctypes.Certificate;
import ru.gb.antonov.doctypes.ICertificate;
import ru.gb.antonov.structs.Causes;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

public class CertificateMapper {

    public static Collection<ICertificate> mapResults (ResultSet resultSet) throws SQLException {
        Collection<ICertificate> collection = new ArrayList<>();

        if (resultSet != null)
        while (resultSet.next()) {
            ICertificate certificate = Certificate.emptyCertificate();
            if (certificate.setId (resultSet.getLong ("id"))) {

                certificate.setCause (Causes.valueOf (resultSet.getString ("causes")));
                certificate.setCustomerId (resultSet.getLong ("customer_id"));
                certificate.setTimeStamp (resultSet.getTimestamp ("timestamp").toLocalDateTime());
                collection.add (certificate);
            }
            else throw new RuntimeException();
        }
        return collection;
    }
}/*
CREATE TABLE certificates (
    id          INT8         PRIMARY KEY,
    timestamp   TIMESTAMP    NOT NULL,
    causes      VARCHAR (16) NOT NULL,
    customer_id INT8         NOT NULL
);  */
