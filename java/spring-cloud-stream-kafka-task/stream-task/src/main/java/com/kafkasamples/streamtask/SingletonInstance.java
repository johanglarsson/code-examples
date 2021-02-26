package com.kafkasamples.streamtask;

public class SingletonInstance {

    private static final SingletonInstance INSTANCE = new SingletonInstance();

    private SingletonInstance() {
    }

    public static SingletonInstance getInstance() {
        return INSTANCE;
    }
}
