package com.github.jason1114.hangman.bean.response;

/**
 *
 * Created by Jason on 15/10/27.
 */
public class GameInfo {
    Integer numberOfWordsToGuess;
    Integer numberOfGuessAllowedForEachWord;

    public Integer getNumberOfWordsToGuess() {
        return numberOfWordsToGuess;
    }

    public void setNumberOfWordsToGuess(Integer numberOfWordsToGuess) {
        this.numberOfWordsToGuess = numberOfWordsToGuess;
    }

    public Integer getNumberOfGuessAllowedForEachWord() {
        return numberOfGuessAllowedForEachWord;
    }

    public void setNumberOfGuessAllowedForEachWord(Integer numberOfGuessAllowedForEachWord) {
        this.numberOfGuessAllowedForEachWord = numberOfGuessAllowedForEachWord;
    }
}
