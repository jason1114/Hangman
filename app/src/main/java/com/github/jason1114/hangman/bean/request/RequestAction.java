package com.github.jason1114.hangman.bean.request;

/**
 *
 * Created by Jason on 15/10/28.
 */
public abstract class RequestAction {
    String action;

    public RequestAction(String action) {
        this.action = action;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
