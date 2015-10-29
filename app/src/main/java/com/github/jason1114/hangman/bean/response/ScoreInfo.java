package com.github.jason1114.hangman.bean.response;

/**
 *
 * Created by Jason on 15/10/28.
 */
public class ScoreInfo {
    Integer totalWordCount;
    Integer correctWordCount;
    Integer totalWrongGuessCount;
    Integer score;

    public Integer getTotalWordCount() {
        return totalWordCount;
    }

    public void setTotalWordCount(Integer totalWordCount) {
        this.totalWordCount = totalWordCount;
    }

    public Integer getCorrectWordCount() {
        return correctWordCount;
    }

    public void setCorrectWordCount(Integer correctWordCount) {
        this.correctWordCount = correctWordCount;
    }

    public Integer getTotalWrongGuessCount() {
        return totalWrongGuessCount;
    }

    public void setTotalWrongGuessCount(Integer totalWrongGuessCount) {
        this.totalWrongGuessCount = totalWrongGuessCount;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }
}
