package ru.gb.antonov.doctypes;

import ru.gb.antonov.structs.Causes;

import java.time.LocalDateTime;

public interface ICertificate {

    Long    getId ();
    boolean setId (long value);

    LocalDateTime getTimeStamp ();
    void setTimeStamp (LocalDateTime ldt);

    Causes getCause ();
    void setCause (Causes value);

    Long getCustomerId ();
    void setCustomerId (Long value);
}
