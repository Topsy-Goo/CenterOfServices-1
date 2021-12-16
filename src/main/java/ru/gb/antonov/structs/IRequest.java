package ru.gb.antonov.structs;

public interface IRequest {

    Long getId ();

    ICustomer getCustomer ();

    Integer   getPriority ();

    void      setPriority (Integer priority);
}
