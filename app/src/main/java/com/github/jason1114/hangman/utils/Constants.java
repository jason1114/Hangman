package com.github.jason1114.hangman.utils;

/**
 * Used to store application level constants
 * Created by Jason on 15/10/27.
 */
public class Constants {

    /**
     * General request url
     */
    public static final String REQUEST_URL = "https://strikingly-hangman.herokuapp.com/game/on";

    /**
     * Actions
     */
    public static final String ACTION_START = "startGame";
    public static final String ACTION_NEXT_WORD = "nextWord";
    public static final String ACTION_GUESS = "guessWord";
    public static final String ACTION_GET_RESULT = "getResult";
    public static final String ACTION_SUBMIT = "submitResult";
}
