package com.github.jason1114.hangman.bean.request;

import com.github.jason1114.hangman.utils.Constants;

/**
 *
 * Created by Jason on 15/10/28.
 */
public class GuessWordAction extends SessionRequestAction {
    String guess;
    public GuessWordAction(String guess, String sessionId) {
        super(Constants.ACTION_GUESS, sessionId);
        this.guess = guess;
    }

    public String getGuess() {
        return guess;
    }

    public void setGuess(String guess) {
        this.guess = guess;
    }
}
