package com.ys.network.base;

public interface IPresenter<T> {
    void attachView(T view);
    void detachView();
}
