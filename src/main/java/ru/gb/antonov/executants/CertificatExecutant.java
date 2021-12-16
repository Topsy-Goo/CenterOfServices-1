package ru.gb.antonov.executants;

import ru.gb.antonov.doctypes.Certificate;
import ru.gb.antonov.doctypes.ICertificate;
import ru.gb.antonov.structs.IRequest;

public class CertificatExecutant implements IExecutant <ICertificate, IRequest> {

//-------------- реализация шаблона builder для создания объекта ICertificate ------------

    @Override public ICertificate execute (IRequest request) {
        return (request == null) ? null : Certificate.builder()
                                                     .withCustomerId (request.getCustomer().getId())
                                                     .withCause (request.getCustomer().getCause())
                                                     .build();
    }
}
