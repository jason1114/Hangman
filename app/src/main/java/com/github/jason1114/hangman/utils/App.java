package com.github.jason1114.hangman.utils;

import android.app.Application;
import android.os.Handler;

import com.github.jason1114.hangman.bean.response.GameInfo;

/**
 * Custom Application class
 * Used to store some global variables
 * Created by Jason on 15/10/27.
 */
public class App extends Application {

    // region Static Area
    private static App instance;

    public static App get() {
        return instance;
    }
    // endregion

    GameInfo mGameInfo;
    String mAccount;
    String mSession;
    Handler mHandler;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        mHandler = new Handler();
    }

    public Handler getHandler() {
        return mHandler;
    }

    public void setHandler(Handler mHandler) {
        this.mHandler = mHandler;
    }

    public GameInfo getGameInfo() {
        return mGameInfo;
    }

    public void setGameInfo(GameInfo mGameInfo) {
        this.mGameInfo = mGameInfo;
    }

    public String getAccount() {
        return mAccount;
    }

    public void setAccount(String mAccount) {
        this.mAccount = mAccount;
    }

    public String getSession() {
        return mSession;
    }

    public void setSession(String mSession) {
        this.mSession = mSession;
    }
}
