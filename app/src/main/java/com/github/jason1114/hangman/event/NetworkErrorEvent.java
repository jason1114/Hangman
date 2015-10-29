package com.github.jason1114.hangman.event;

/**
 * Event of volley network error happens
 * Created by Jason on 15/10/28.
 */
public class NetworkErrorEvent {
    String action;
    Throwable error;

    public NetworkErrorEvent(String action, Throwable error) {
        this.action = action;
        this.error = error;
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
