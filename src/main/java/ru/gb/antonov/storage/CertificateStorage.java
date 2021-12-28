package ru.gb.antonov.storage;

import ru.gb.antonov.doctypes.ICertificate;

import java.io.IOException;
import java.sql.*;
import java.util.Collection;

import static ru.gb.antonov.Factory.lnprint;
import static ru.gb.antonov.Factory.println;

public class CertificateStorage implements IStorage<ICertificate> {

    private static       CertificateStorage instance;
    private static final Object             MONITOR = new Object();

    private long               nextId;
    private Connection         connection;
    private Statement          statement;
    private PreparedStatement  ps4Saving;

    private CertificateStorage () {
        try {
            connection = DriverManager.getConnection ("jdbc:sqlite:cos1.db");
            Class.forName ("org.sqlite.JDBC");
            statement = connection.createStatement();
            ps4Saving = connection.prepareStatement (
                "INSERT INTO certificates (id, timestamp, causes, customer_id) VALUES (?, ?, ?, ?);");

            ResultSet rs = statement.executeQuery ("SELECT MAX(id) FROM certificates;");
            if (rs.next())//first())    < java.sql.SQLException: ResultSet is TYPE_FORWARD_ONLY
                nextId = rs.getLong(1) +1;
        }
        catch (SQLException | ClassNotFoundException e) { throw new RuntimeException (e); }
        lnprint ("Экземпляр CertificateStorage создан.");
    }

    @Override public void stop () {
        try {
            if (statement != null)  statement.close();
            if (connection != null) connection.close();
            if (ps4Saving != null)  ps4Saving.close();
        }
        catch (SQLException e) { e.printStackTrace(); }
        finally {
            connection = null;
            statement = null;
            ps4Saving = null;
            lnprint ("Вызван CertificateStorage.stop().");
        }
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

    @Override public ICertificate save (ICertificate certificate) throws SQLException, IOException {
        if (certificate == null)
            return null;//throw new NullPointerException();

        Long id = certificate.getId();
        if (id != null  ||  certificate.setId (id = nextId++)) {

            ps4Saving.setLong (1, id);
            ps4Saving.setTimestamp (2, Timestamp.valueOf (certificate.getTimeStamp()));
            ps4Saving.setString (3, certificate.getCause().name());
            ps4Saving.setLong (4, certificate.getCustomerId());

            ps4Saving.executeUpdate(); // INSERT, UPDATE or DELETE
        }
        else throw new IOException();
        return certificate;
    }

    @Override public Collection<ICertificate> findAllByCustomerId (Long cid) throws SQLException {

        String param = String.format ("SELECT * FROM certificates WHERE customer_id = %d;", cid);
        ResultSet resultSet = statement.executeQuery (param); // never returns null

        return CertificateMapper.mapResults (resultSet);
    }
}/*
CREATE TABLE certificates (
    id          INT8         PRIMARY KEY,
    timestamp   TIMESTAMP    NOT NULL,
    causes      VARCHAR (16) NOT NULL,
    customer_id INT8         NOT NULL
);  */
