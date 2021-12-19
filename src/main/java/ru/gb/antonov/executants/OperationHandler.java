package ru.gb.antonov.executants;

abstract public class OperationHandler <C, R, T> {

    protected IExecutant<C,T> executant;

    protected OperationHandler (IExecutant<C,T> e) { executant = e; }

    abstract public R handle (T t);
}
