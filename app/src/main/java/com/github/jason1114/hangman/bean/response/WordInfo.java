package com.github.jason1114.hangman.bean.response;

/**
 *
 * Created by Jason on 15/10/28.
 */
public class WordInfo {
    String word;
    Integer totalWordCount;
    Integer wrongGuessCountOfCurrentWord;

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public Integer getTotalWordCount() {
        return totalWordCount;
    }

    public void setTotalWordCount(Integer totalWordCount) {
        this.totalWordCount = totalWordCount;
    }

    public Integer getWrongGuessCountOfCurrentWord() {
        return wrongGuessCountOfCurrentWord;
    }

    public void setWrongGuessCountOfCurrentWord(Integer wrongGuessCountOfCurrentWord) {
        this.wrongGuessCountOfCurrentWord = wrongGuessCountOfCurrentWord;
    }
}
