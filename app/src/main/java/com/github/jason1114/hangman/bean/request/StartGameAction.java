package com.github.jason1114.hangman.bean.request;

import com.github.jason1114.hangman.utils.Constants;

/**
 *
 * Created by Jason on 15/10/28.
 */
public class StartGameAction extends RequestAction {
    String playerId;

    public StartGameAction(String playerId) {
        super(Constants.ACTION_START);
        this.playerId = playerId;
    }

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }
}
