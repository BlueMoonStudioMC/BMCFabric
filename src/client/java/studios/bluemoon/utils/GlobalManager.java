package studios.bluemoon.utils;

import java.util.concurrent.ConcurrentHashMap;

public interface GlobalManager<T> {

    T getCurrent();
    void setActive(T t);

}

