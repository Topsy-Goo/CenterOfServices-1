package ru.gb.antonov.structs;

public class Customer {

    private final Long   id;
    private final Causes cause;

    public Customer (Long id, Causes cause) {
        this.id = id;
        this.cause = cause;
    }

    public Long   getId ()    { return id; }
    public Causes getCause () { return cause; }
}
