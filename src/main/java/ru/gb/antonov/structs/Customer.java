package ru.gb.antonov.structs;

public class Customer implements ICustomer {

    private final Long   id;
    private final Causes cause;
    private final CosOperations operation;

    public Customer (Long id, Causes cause, CosOperations operation) {
        this.id = id;
        this.cause = cause;
        this.operation = operation;
    }

    @Override public Long          getId ()        { return id; }
    @Override public Causes        getCause ()     { return cause; }
    @Override public CosOperations getOperation () { return operation; }
}
