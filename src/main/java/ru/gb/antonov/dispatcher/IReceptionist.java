package ru.gb.antonov.dispatcher;

import ru.gb.antonov.doctypes.ISertificate;
import ru.gb.antonov.structs.ICustomer;

import java.util.Collection;

public interface IReceptionist {

    boolean receive (ICustomer customer);

    Collection<ISertificate> getSertificatesByCause (ICustomer customer);

    Collection<ISertificate> getSertificates (ICustomer customer);
}
