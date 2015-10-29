package com.github.jason1114.hangman.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;

import com.github.jason1114.hangman.R;
import com.github.jason1114.hangman.bean.request.GuessWordAction;
import com.github.jason1114.hangman.bean.request.StartGameAction;
import com.github.jason1114.hangman.bean.response.GameInfo;
import com.github.jason1114.hangman.bean.response.ScoreInfo;
import com.github.jason1114.hangman.bean.response.SubmitResultInfo;
import com.github.jason1114.hangman.bean.response.WordInfo;
import com.github.jason1114.hangman.event.GameStartEvent;
import com.github.jason1114.hangman.event.NetworkErrorEvent;
import com.github.jason1114.hangman.event.ResultSubmitEvent;
import com.github.jason1114.hangman.event.ScoreInfoUpdateEvent;
import com.github.jason1114.hangman.event.ServerDataErrorEvent;
import com.github.jason1114.hangman.event.WordInfoUpdateEvent;
import com.github.jason1114.hangman.network.RestClient;
import com.github.jason1114.hangman.utils.App;
import com.github.jason1114.hangman.utils.Constants;
import com.github.jason1114.hangman.utils.ToastUtils;

import java.util.HashSet;
import java.util.Set;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;
import de.greenrobot.event.EventBus;

public class MainActivity extends AppCompatActivity {

    public static Intent makeIntent() {
        Intent intent = new Intent(App.get(), MainActivity.class);
        return intent;
    }

    @Bind(R.id.score) TextView mScoreView;
    @Bind(R.id.total) TextView mTotalView;
    @Bind(R.id.tried) TextView mTriedView;
    @Bind(R.id.correct) TextView mCorrectView;
    @Bind(R.id.wrong_guess) TextView mWrongGuessView;
    @Bind(R.id.word_to_guess) TextView mWordToGuessView;
    @Bind(R.id.current_wrong_guess) TextView mCurrentWrongGuessView;
    @Bind(R.id.guess_input) GridView mGuessInputView;
    @Bind(R.id.loading_layout) View mLoadingView;
    @Bind(R.id.guess_layout) View mGuessLayoutView;
    @Bind(R.id.new_word) View mNextWordView;
    @Bind(R.id.submit) View mSubmitView;
    @Bind(R.id.refresh) View mRefreshView;

    int mScore, mTotal, mTried, mCorrect, mWrongGuess, mCurrentWrongGuess;
    int mNumberOfGuessAllowedForEachWord;
    String mWordToGuess = "";

    /**
     * If the score refresh request failed for some reason,
     * Then the score is invalidate, it is displayed like '1000?',
     * as well as correct word count and wrong guess count
     */
    boolean scoreInvalidate = false;
    KeyButtonAdapter mAdapter;

    // region Life Cycle methods

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mAdapter = new KeyButtonAdapter();
        mGuessInputView.setAdapter(mAdapter);

        GameInfo info = App.get().getGameInfo();
        mTotal = info.getNumberOfWordsToGuess();
        mNumberOfGuessAllowedForEachWord = info.getNumberOfGuessAllowedForEachWord();
        refreshView();
        newWord();
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

    // region Helpers

    /**
     * Most Ui refresh logic is here
     */
    private void refreshView() {
        mTotalView.setText(getString(R.string.total, mTotal));
        if (scoreInvalidate) {
            mScoreView.setText(getString(R.string.score_invalidate, mScore));
        } else {
            mScoreView.setText(getString(R.string.score, mScore));
        }
        mTriedView.setText(getString(R.string.tried, mTried));
        if (scoreInvalidate) {
            mCorrectView.setText(getString(R.string.correct_invalidate, mCorrect));
        } else {
            mCorrectView.setText(getString(R.string.correct, mCorrect));
        }
        if (scoreInvalidate) {
            mWrongGuessView.setText(getString(R.string.wrong_guess_invalidate, mWrongGuess));
        } else {
            mWrongGuessView.setText(getString(R.string.wrong_guess, mWrongGuess));
        }
        // All words is guessed
        if (mTried == mTotal) {
            mNextWordView.setEnabled(false);
        }
        mCurrentWrongGuessView.setText(getString(R.string.current_wrong_guess,
                mCurrentWrongGuess, mNumberOfGuessAllowedForEachWord));
        mWordToGuessView.setText(mWordToGuess);
        mAdapter.notifyDataSetChanged();
    }

    // endregion

    // region Event handler
    /**
     * Event handler of clicking "NEXT WORD"
     * It is also auto-called when game start and restart
     */
    @OnClick(R.id.new_word)
    public void newWord() {
        mLoadingView.setVisibility(View.VISIBLE);
        RestClient.nextWord();
    }

    /**
     * Event handler of clicking "RESTART"
     */
    @OnClick(R.id.restart)
    public void restart() {
        mLoadingView.setVisibility(View.VISIBLE);
        StartGameAction action = new StartGameAction(App.get().getAccount());
        RestClient.startGame(action);
    }

    /**
     * Save current score to server
     */
    @OnClick(R.id.submit)
    public void submit() {
        mLoadingView.setVisibility(View.VISIBLE);
        RestClient.submit();
    }

    /**
     * Refresh score and wrong guess count, etc
     * @param view
     */
    @OnClick(R.id.refresh)
    public void refreshScore (View view) {
        if (view != null) {
            mLoadingView.setVisibility(View.VISIBLE);
        }
        RestClient.getResult();
    }

    /**
     * Send a guess request
     * @param position
     */
    public void guess(int position) {
        mLoadingView.setVisibility(View.VISIBLE);
        Character character = (Character) mAdapter.getItem(position);
        RestClient.guessWord(new GuessWordAction(character.toString(), App.get().getSession()));
    }

    /**
     * Score data submit to server ok callback
     * @param event
     */
    public void onEventMainThread(ResultSubmitEvent event) {
        mLoadingView.setVisibility(View.INVISIBLE);
        ToastUtils.s(R.string.submit_ok);
        SubmitResultInfo info = event.getSubmitResultInfo().getData();
        mTried = info.getTotalWordCount();
        mCorrect = info.getCorrectWordCount();
        mWrongGuess = info.getTotalWrongGuessCount();
        mScore = info.getScore();
        scoreInvalidate = false;
        mAdapter.setDisableAll(true);
        mNextWordView.setEnabled(false);
        mSubmitView.setEnabled(false);
        mRefreshView.setEnabled(false);
        refreshView();
    }

    /**
     * Callback of "Give me a word" action & "make a guess" action
     * @param event
     */
    public void onEventMainThread(WordInfoUpdateEvent event) {
        mLoadingView.setVisibility(View.INVISIBLE);
        mGuessLayoutView.setVisibility(View.VISIBLE);
        WordInfo info = event.getWordInfo().getData();
        if (TextUtils.equals(mWordToGuess, info.getWord())) {
            // this guess is wrong
            mWrongGuess ++;
        }
        mWordToGuess = info.getWord();
        mTried = info.getTotalWordCount();
        mCurrentWrongGuess = info.getWrongGuessCountOfCurrentWord();
        /**
         * Different actions is taken according to
         * whether it is "Give me a word" or
         * "Make a guess"
         */
        switch (event.getAction()) {
            case Constants.ACTION_NEXT_WORD:
                mAdapter.setDisableAll(false);
                mAdapter.getGuessedWords().clear();
                break;
            case Constants.ACTION_GUESS:
                /**
                 * guess the whole word successfully
                 */
                boolean guessOk = !TextUtils.isEmpty(mWordToGuess)
                        && TextUtils.indexOf(mWordToGuess, "*") == -1;
                /**
                 * wrong guess count is used out,
                 * guess of the word fail
                 */
                boolean guessFail = mCurrentWrongGuess >= mNumberOfGuessAllowedForEachWord;
                if (guessOk) {
                    ToastUtils.s(R.string.guess_right_hint);
                    mCorrect ++;
                }
                if (guessFail) {
                    ToastUtils.s(R.string.guess_fail_hint);
                }
                if (guessFail || guessOk) {
                    mAdapter.setDisableAll(true);
                }
                mAdapter.getGuessedWords()
                        .add(mAdapter.getLastClickedPosition());
                break;
        }
        scoreInvalidate = true;
        refreshView();
        refreshScore(null);
    }

    /**
     * Callback of the action "Get the result"
     * @param event
     */
    public void onEventMainThread(ScoreInfoUpdateEvent event) {
        mLoadingView.setVisibility(View.INVISIBLE);
        ScoreInfo info = event.getScoreInfo().getData();
        mTried = info.getTotalWordCount();
        mCorrect = info.getCorrectWordCount();
        mWrongGuess = info.getTotalWrongGuessCount();
        mScore = info.getScore();
        scoreInvalidate = false;
        refreshView();
    }

    /**
     * Callback when volley encounter a network error
     * @param event
     */
    public void onEventMainThread(NetworkErrorEvent event) {
        mLoadingView.setVisibility(View.INVISIBLE);
        switch (event.getAction()) {
            case Constants.ACTION_GET_RESULT:
                ToastUtils.s(R.string.refresh_score_fail);
                break;
            default:
                ToastUtils.s(R.string.network_error);
        }
    }

    /**
     * Callback when data return from server is illegal
     * @param event
     */
    public void onEventMainThread(ServerDataErrorEvent event) {
        mLoadingView.setVisibility(View.INVISIBLE);
        switch (event.getAction()) {
            case Constants.ACTION_GET_RESULT:
                ToastUtils.s(R.string.refresh_score_fail);
                break;
            default:
                ToastUtils.s(R.string.server_data_error);
        }
    }

    /**
     * Callback when game is restarted
     * @param event
     */
    public void onEventMainThread(GameStartEvent event) {
        ToastUtils.s(R.string.new_game_started);
        App.get().setSession(event.getGameInfo().getSessionId());
        App.get().setGameInfo(event.getGameInfo().getData());
        mNextWordView.setEnabled(true);
        mSubmitView.setEnabled(true);
        mRefreshView.setEnabled(true);
        mGuessLayoutView.setVisibility(View.INVISIBLE);
        GameInfo info = App.get().getGameInfo();
        mTotal = info.getNumberOfWordsToGuess();
        mNumberOfGuessAllowedForEachWord = info.getNumberOfGuessAllowedForEachWord();
        mScore = 0;
        mTried = 0;
        mCorrect = 0;
        mWrongGuess = 0;
        mCurrentWrongGuess = 0;
        scoreInvalidate = false;
        refreshView();
        newWord();
    }
    // endregion

    // region Adapter

    /**
     * This adapter is used as guess input like a keyboard
     */
    class KeyButtonAdapter extends BaseAdapter {

        /**
         * whether to disable all keys
         */
        boolean disableAll = false;
        /**
         * store keys already guessed
         */
        Set<Integer> guessedWords = new HashSet<>();
        /**
         * Last guess key position
         */
        int lastClickedPosition = -1;

        public boolean isDisableAll() {
            return disableAll;
        }

        public void setDisableAll(boolean disableAll) {
            this.disableAll = disableAll;
        }

        public Set<Integer> getGuessedWords() {
            return guessedWords;
        }

        public void setGuessedWords(Set<Integer> guessedWords) {
            this.guessedWords = guessedWords;
        }

        public int getLastClickedPosition() {
            return lastClickedPosition;
        }

        public void setLastClickedPosition(int lastClickedPosition) {
            this.lastClickedPosition = lastClickedPosition;
        }

        @Override
        public int getCount() {
            return 26;
        }

        @Override
        public Object getItem(int position) {
            return new Character((char) ('A' + position));
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            KeyButtonViewHolder holder;
            if (convertView == null) {
                LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
                convertView = inflater.inflate(R.layout.item_key_btn, null);
                holder = new KeyButtonViewHolder(convertView);
            } else {
                holder = (KeyButtonViewHolder) convertView.getTag();
            }
            holder.btn.setText(getItem(position).toString());
            if (disableAll || guessedWords.contains(position)) {
                holder.btn.setEnabled(false);
            } else {
                holder.btn.setEnabled(true);
            }
            holder.btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    guess(position);
                    lastClickedPosition = position;
                }
            });
            return convertView;
        }
    }
    class KeyButtonViewHolder {
        @Bind(R.id.key)
        Button btn;

        public KeyButtonViewHolder(View view) {
            ButterKnife.bind(this, view);
            view.setTag(this);
        }
    }
    // endregion
}
