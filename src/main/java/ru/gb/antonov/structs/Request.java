package ru.gb.antonov.structs;

public class Request implements IRequest {

    private static       Long nextId = 0L;
    private        final Long id;
    private        final ICustomer customer;
    private              Integer priority;

    public Request (ICustomer customer, Integer priority) {
        this.customer = customer;
        this.priority = priority;
        id = nextId++;
    }

    @Override public Long getId () { return id; }

    @Override public ICustomer getCustomer ()  { return customer; }

    @Override public void setPriority (Integer value) { priority = value; }

    @Override public Integer getPriority () { return priority; }
}
