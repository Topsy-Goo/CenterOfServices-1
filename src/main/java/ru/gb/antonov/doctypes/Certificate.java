package ru.gb.antonov.doctypes;

import ru.gb.antonov.structs.Causes;
import java.time.LocalDateTime;

import static ru.gb.antonov.Factory.lnprint;

public class Certificate implements ICertificate {

    private Long          id;
    private LocalDateTime timeStamp;
    private Long          customerId;
    private Causes        cause;

    private Certificate () {
        timeStamp = LocalDateTime.now();
    }

    @Override public Long getId ()         { return id; }
    @Override public boolean setId (long value) {
        if (id == null)
            id = value;
        return id == value;
    }
    @Override public LocalDateTime getTimeStamp ()   { return timeStamp; }
    @Override public void setTimeStamp (LocalDateTime value) { timeStamp = value; }

    @Override public Long getCustomerId () { return customerId; }
    @Override public void setCustomerId (Long value) { customerId = value; }

    @Override public Causes getCause ()    { return cause; }
    @Override public void setCause (Causes value)    { cause = value; }

    public static Certificate emptyCertificate () {
        return new Certificate();
    }

    @Override public String toString () {
        return String.format ("{id:%d, %s, customer:%d, cause:%s}", id, timeStamp, customerId, cause.name());
    }

//-------------- реализация шаблона builder для создания объекта Certificate -------------

    public static Builder builder() { return new Builder(); }

/** Вложенный класс может использовать приватные члены внешнего класса. */
    public static class Builder {

        private final Certificate certificate = emptyCertificate();

        private Builder(){}

        public Builder withCustomerId (Long cid) {
            certificate.customerId = cid;
            return this;
        }
        public Builder withCause (Causes c) {
            certificate.cause = c;
            return this;
        }
        public ICertificate build () {
            lnprint ("Создан сертификат "+certificate+".");
            return certificate;
        }
    }
}
