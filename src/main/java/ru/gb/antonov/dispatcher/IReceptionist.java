package ru.gb.antonov.dispatcher;

import ru.gb.antonov.structs.Customer;
import ru.gb.antonov.structs.Sertificate;

import java.util.Collection;

public interface IReceptionist {

    boolean receive (Customer customer);
    Collection<Sertificate> getSertificatesByCause (Customer customer);
    Collection<Sertificate> getSertificates (Customer customer);
}
