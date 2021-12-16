package ru.gb.antonov.executants;

abstract public class Handler<C, R, T> {

    protected IExecutant<C,T> executant;

    protected Handler (IExecutant<C,T> e) { executant = e; }

    abstract public R handle (T t);
}
