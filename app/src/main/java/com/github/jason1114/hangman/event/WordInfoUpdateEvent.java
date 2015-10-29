package com.github.jason1114.hangman.event;

import com.github.jason1114.hangman.bean.response.ActionResponse;
import com.github.jason1114.hangman.bean.response.WordInfo;

/**
 * event of "Give a word" & "Make a guess"
 * Created by Jason on 15/10/28.
 */
public class WordInfoUpdateEvent {
    ActionResponse<WordInfo> wordInfo;
    String action;

    public WordInfoUpdateEvent(String action, ActionResponse<WordInfo> wordInfo) {
        this.wordInfo = wordInfo;
        this.action = action;
    }

    public ActionResponse<WordInfo> getWordInfo() {
        return wordInfo;
    }

    public void setWordInfo(ActionResponse<WordInfo> wordInfo) {
        this.wordInfo = wordInfo;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
