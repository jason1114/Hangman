package com.github.jason1114.hangman.bean.request;

/**
 *
 * Created by Jason on 15/10/28.
 */
public class SessionRequestAction extends RequestAction {
    String sessionId;

    public SessionRequestAction(String action, String sessionId) {
        super(action);
        this.sessionId = sessionId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
}
