package com.diluv.api.utils;

@FunctionalInterface
public interface Callback<T> {
    void invoke(T object);
}