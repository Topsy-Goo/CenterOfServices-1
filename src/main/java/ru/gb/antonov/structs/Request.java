package ru.gb.antonov.structs;

public class Request {

    private final Long customerId;
    private final Causes cause;
    private       Integer priority;

    public Request (Long cid, Causes c, Integer p) {
        customerId = cid;
        cause = c;
        priority = p;
    }

    public Long getCustomerId ()  { return customerId; }

    public Causes getCause ()     { return cause; }

    public Integer getPriority () { return priority; }

    public void setPriority (Integer priority) { this.priority = priority; }
}
