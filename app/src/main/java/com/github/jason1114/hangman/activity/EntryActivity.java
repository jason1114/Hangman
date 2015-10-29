package com.github.jason1114.hangman.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.github.jason1114.hangman.R;
import com.github.jason1114.hangman.bean.request.StartGameAction;
import com.github.jason1114.hangman.bean.response.GameInfo;
import com.github.jason1114.hangman.event.GameStartEvent;
import com.github.jason1114.hangman.event.NetworkErrorEvent;
import com.github.jason1114.hangman.event.ServerDataErrorEvent;
import com.github.jason1114.hangman.network.RestClient;
import com.github.jason1114.hangman.utils.App;
import com.github.jason1114.hangman.utils.ToastUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

public class EntryActivity extends AppCompatActivity {

    @Bind(R.id.id_input) EditText mIdInput;
    @Bind(R.id.login_btn) View mLoginBtn;

    // region Life Cycle methods
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry);
        ButterKnife.bind(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    // endregion



    // region Event handler

    /**
     * Click login btn and send star game request
     */
    @OnClick(R.id.login_btn)
    public void submit() {
        String id = mIdInput.getText().toString();
        App.get().setAccount(id);
        StartGameAction action = new StartGameAction(id);
        mLoginBtn.setEnabled(false);
        mIdInput.setEnabled(false);
        RestClient.startGame(action);
    }

    /**
     * Callback when server returns successful game start response
     * @param event including game info
     */
    public void onEventMainThread(GameStartEvent event) {
        ToastUtils.s(R.string.new_game_started);
        App.get().setSession(event.getGameInfo().getSessionId());
        App.get().setGameInfo(event.getGameInfo().getData());
        Intent intent = MainActivity.makeIntent();
        startActivity(intent);
        finish();
    }

    /**
     * Callback when network is broken
     * @param event
     */
    public void onEventMainThread(NetworkErrorEvent event) {
        mLoginBtn.setEnabled(true);
        mIdInput.setEnabled(true);
        ToastUtils.s(R.string.network_error);
    }

    /**
     * Callback when server returns illegal response data
     * @param event
     */
    public void onEventMainThread(ServerDataErrorEvent event) {
        mLoginBtn.setEnabled(true);
        mIdInput.setEnabled(true);
        ToastUtils.s(R.string.server_data_error);
    }
    // endregion
}
