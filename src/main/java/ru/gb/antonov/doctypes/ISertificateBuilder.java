package ru.gb.antonov.doctypes;

import ru.gb.antonov.structs.Causes;

public interface ISertificateBuilder<S> {

    S withCustomerId (Long cid);

    S withCause (Causes c);

    S build();
}
