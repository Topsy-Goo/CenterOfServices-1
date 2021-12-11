package ru.gb.antonov.doctypes;

import ru.gb.antonov.structs.Causes;
import java.time.LocalDateTime;

public class Sertificate implements ISertificate {

    private static long nextId = 0L;
    private final  Long id;
    private final  LocalDateTime timeStamp;
    private        Long          customerId;
    private        Causes        cause;

    private Sertificate () {
        id = nextId ++;
        timeStamp = LocalDateTime.now();
    }

    public Long getId ()                   { return id; }
    public LocalDateTime getTimeStamp ()   { return timeStamp; }
    @Override public Long getCustomerId () { return customerId; }
    @Override public Causes getCause ()    { return cause; }

//-------------- реализация шаблона builder для создания объекта Sertificate -------------

    public static ISertificate createEmpty () { return new Sertificate(); }

    @Override public ISertificate withCustomerId (Long cid) {
        customerId = cid;
        return this;
    }

    @Override public ISertificate withCause (Causes c) {
        cause = c;
        return this;
    }

    @Override public ISertificate build () { return this; }
//*/
/*    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private final Sertificate sertificate = new Sertificate();

        private Builder(){}

        public Builder withCustomerId (Long cid) {
            sertificate.customerId = cid;
            return this;
        }
        public Builder withCause (Causes c) {
            sertificate.cause = c;
            return this;
        }
        public Sertificate build () { return sertificate; }
    }//*/
}
