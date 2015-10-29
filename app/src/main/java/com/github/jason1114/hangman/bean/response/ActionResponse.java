package com.github.jason1114.hangman.bean.response;

/**
 *
 * Created by Jason on 15/10/27.
 */
public class ActionResponse<T> {
    String message;
    String sessionId;
    T data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
