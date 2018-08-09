package com.ys.network.progress;

public interface SubscriberOnResponseListenter<T> {
    void next(T t);
    void error(String t);
}
