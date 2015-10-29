package com.github.jason1114.hangman.event;

import com.github.jason1114.hangman.bean.response.ActionResponse;
import com.github.jason1114.hangman.bean.response.SubmitResultInfo;

/**
 * Event of "Submit Result"
 * Created by Jason on 15/10/28.
 */
public class ResultSubmitEvent {
    ActionResponse<SubmitResultInfo> submitResultInfo;

    public ResultSubmitEvent(ActionResponse<SubmitResultInfo> submitResultInfo) {
        this.submitResultInfo = submitResultInfo;
    }

    public ActionResponse<SubmitResultInfo> getSubmitResultInfo() {
        return submitResultInfo;
    }

    public void setSubmitResultInfo(ActionResponse<SubmitResultInfo> submitResultInfo) {
        this.submitResultInfo = submitResultInfo;
    }
}
