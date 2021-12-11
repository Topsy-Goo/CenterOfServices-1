package ru.gb.antonov.executant;

import ru.gb.antonov.doctypes.ISertificateBuilder;

public interface IExecutant <R extends ISertificateBuilder<?>, T> extends Runnable {

    R execute (T t);
}
