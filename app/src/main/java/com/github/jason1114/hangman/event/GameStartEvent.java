package com.github.jason1114.hangman.event;

import com.github.jason1114.hangman.bean.response.ActionResponse;
import com.github.jason1114.hangman.bean.response.GameInfo;

/**
 * Event of staring game & restarting game
 * Created by Jason on 15/10/27.
 */
public class GameStartEvent {
    ActionResponse<GameInfo> gameInfo;

    public GameStartEvent(ActionResponse<GameInfo> gameInfo) {
        this.gameInfo = gameInfo;
    }

    public ActionResponse<GameInfo> getGameInfo() {
        return gameInfo;
    }

    public void setGameInfo(ActionResponse<GameInfo> gameInfo) {
        this.gameInfo = gameInfo;
    }
}
