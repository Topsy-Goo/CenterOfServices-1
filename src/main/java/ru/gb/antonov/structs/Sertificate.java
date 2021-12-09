package ru.gb.antonov.structs;

import java.time.LocalDateTime;

public class Sertificate {

    private static long nextId = 0L;
    private final  Long id;
    private final  Long customerId;
    private final  Causes        cause;
    private final  LocalDateTime timeStamp;

    public Sertificate (Long cid, Causes c) {
        id = nextId ++;
        customerId = cid;
        cause = c;
        timeStamp = LocalDateTime.now();
    }

    public Long getId ()                 { return id; }
    public Long getCustomerId ()         { return customerId; }
    public Causes getCause ()            { return cause; }
    public LocalDateTime getTimeStamp () { return timeStamp; }
}
