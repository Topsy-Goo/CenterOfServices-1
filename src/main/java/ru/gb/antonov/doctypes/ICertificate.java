package ru.gb.antonov.doctypes;

import ru.gb.antonov.structs.Causes;

public interface ICertificate {

    Causes getCause ();

    Long getCustomerId ();
}
