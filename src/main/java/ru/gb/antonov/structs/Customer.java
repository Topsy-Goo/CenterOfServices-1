package ru.gb.antonov.structs;

public class Customer implements ICustomer {

    private final Long   id;
    private final Causes cause;

    public Customer (Long id, Causes cause) {
        this.id = id;
        this.cause = cause;
    }

    @Override public Long   getId ()    { return id; }
    @Override public Causes getCause () { return cause; }
}
