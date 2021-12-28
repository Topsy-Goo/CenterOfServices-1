package ru.gb.antonov.executants;

import ru.gb.antonov.doctypes.Certificate;
import ru.gb.antonov.doctypes.ICertificate;
import ru.gb.antonov.structs.Causes;
import ru.gb.antonov.structs.IRequest;

import static ru.gb.antonov.Factory.lnprint;

public class CertificatExecutant implements IExecutant <ICertificate, IRequest> {

    public CertificatExecutant (Causes c) {
        lnprint ("Экземпляр CertificatExecutant ("+c.name()+") создан.");
    }

//-------------- реализация шаблона builder для создания объекта ICertificate ------------

    @Override public ICertificate execute (IRequest request) {
        return (request == null) ? null : Certificate.builder()
                                                     .withCustomerId (request.getCustomer().getId())
                                                     .withCause (request.getCustomer().getCause())
                                                     .build();
    }
}
