package ru.gb.antonov.doctypes;

import ru.gb.antonov.structs.Causes;
import java.time.LocalDateTime;

public class Certificate implements ICertificate {

    private static long nextId = 0L;
    private final  Long id;
    private final  LocalDateTime timeStamp;
    private        Long          customerId;
    private        Causes        cause;

    private Certificate () {
        id = nextId ++;
        timeStamp = LocalDateTime.now();
    }

    public Long getId ()                   { return id; }
    public LocalDateTime getTimeStamp ()   { return timeStamp; }
    @Override public Long getCustomerId () { return customerId; }
    @Override public Causes getCause ()    { return cause; }

//-------------- реализация шаблона builder для создания объекта Certificate -------------

    public static Builder builder() { return new Builder(); }

/** Вложенный класс может использовать приватные члены внешнего класса. */
    public static class Builder {

        private final Certificate certificate = new Certificate();

        private Builder(){}

        public Builder withCustomerId (Long cid) {
            certificate.customerId = cid;
            return this;
        }
        public Builder withCause (Causes c) {
            certificate.cause = c;
            return this;
        }
        public Certificate build () { return certificate; }
    }
}
