package ru.gb.antonov.executants;

public interface IExecutant <R, T> {

    R execute (T t);
}
