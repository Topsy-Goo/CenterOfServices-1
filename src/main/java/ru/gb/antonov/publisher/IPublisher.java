package ru.gb.antonov.publisher;

import ru.gb.antonov.structs.Request;

public interface IPublisher {

    boolean publish (Request request);
}
