package ru.gb.antonov.structs;

import static ru.gb.antonov.Factory.lnprint;

public class Request implements IRequest {

    private static       Long nextId = 0L;
    private        final Long id;
    private        final ICustomer customer;
    private              Integer priority;

    public Request (ICustomer customer, Integer priority) {
        this.customer = customer;
        this.priority = priority;
        id = nextId++;
        lnprint ("Экземпляр Request создан: "+ this +".");
    }

    @Override public Long getId () { return id; }

    @Override public ICustomer getCustomer ()  { return customer; }

    @Override public void setPriority (Integer value) { priority = value; }

    @Override public Integer getPriority () { return priority; }

    @Override public String toString () {
        return String.format ("{id:%d, customer:%d, priority:%d}", id, customer.getId(), priority);
    }
}
