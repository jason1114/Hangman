package com.github.jason1114.hangman.event;

/**
 * Event of server data illegal
 * Created by Jason on 15/10/28.
 */
public class ServerDataErrorEvent {
    String action;
    String data;
    Throwable error;

    public ServerDataErrorEvent(String action, String data, Throwable error) {
        this.action = action;
        this.data = data;
        this.error = error;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Throwable getError() {
        return error;
    }

    public void setError(Throwable error) {
        this.error = error;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
