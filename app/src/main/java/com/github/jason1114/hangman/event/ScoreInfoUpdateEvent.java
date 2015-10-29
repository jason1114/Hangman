package com.github.jason1114.hangman.event;

import com.github.jason1114.hangman.bean.response.ActionResponse;
import com.github.jason1114.hangman.bean.response.ScoreInfo;

/**
 * Event of "Get Result"
 * Created by Jason on 15/10/28.
 */
public class ScoreInfoUpdateEvent {
    ActionResponse<ScoreInfo> scoreInfo;

    public ScoreInfoUpdateEvent(ActionResponse<ScoreInfo> scoreInfo) {
        this.scoreInfo = scoreInfo;
    }

    public ActionResponse<ScoreInfo> getScoreInfo() {
        return scoreInfo;
    }

    public void setScoreInfo(ActionResponse<ScoreInfo> scoreInfo) {
        this.scoreInfo = scoreInfo;
    }
}
