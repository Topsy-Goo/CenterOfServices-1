package ru.gb.antonov.structs;

public interface IRequest {

    ICustomer getCustomer ();

    Integer   getPriority ();

    void      setPriority (Integer priority);
}
