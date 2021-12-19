package ru.gb.antonov.dispatcher;

import ru.gb.antonov.executants.OperationHandler;

abstract public class AbsDispatcher<C,T> {

/** Задаём алгоритм абработки запросов: сперва даём IExecutant'у изготовить сертификат C, а затем
велим одному из OperationHandler'ов поступить с сертификатом так, как указано в запросе T. */
    final protected void workUpRequest (T request) {

        Object result = getOperationHandler (request).handle (request);
        putResult (getKey (request), result);
    }

    abstract OperationHandler<C,?,T> getOperationHandler (T request);

    abstract Object getKey (T request);

    abstract <K,V> void putResult (K key, V result);
}
