package ru.gb.antonov.doctypes;

import ru.gb.antonov.structs.Causes;

public interface ISertificate extends ISertificateBuilder <ISertificate> {

    Causes getCause ();

    Long getCustomerId ();
}
