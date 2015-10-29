package com.github.jason1114.hangman.bean.response;

/**
 *
 * Created by Jason on 15/10/28.
 */
public class SubmitResultInfo {
    String playerId;
    String sessionId;
    Integer totalWordCount;
    Integer correctWordCount;
    Integer totalWrongGuessCount;
    Integer score;
    String datetime;

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

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

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }
}
