package com.github.jason1114.hangman.network;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.jason1114.hangman.bean.request.GuessWordAction;
import com.github.jason1114.hangman.bean.request.SessionRequestAction;
import com.github.jason1114.hangman.bean.request.StartGameAction;
import com.github.jason1114.hangman.bean.response.ActionResponse;
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
import com.github.jason1114.hangman.utils.App;
import com.github.jason1114.hangman.utils.Constants;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.util.HashMap;
import java.util.Map;

import de.greenrobot.event.EventBus;

/**
 * Created by Jason on 15/10/27.
 */
public class RestClient {

    public static final String TAG = "REST_CLIENT";

    private static Gson gson = new Gson();

    private static RequestQueue queue = Volley.newRequestQueue(App.get());

    /**
     * Common request model
     * @param action see ${@link Constants}
     * @param requestData see package request
     * @param listener volley request ok callback
     * @param <T>
     * @throws AuthFailureError
     */
    static<T> void post(final String action, final T requestData, Response.Listener listener) throws AuthFailureError {
        StringRequest request = new StringRequest(
                Request.Method.POST,
                Constants.REQUEST_URL,
                listener,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "NETWORK ERROR", error);
                        EventBus.getDefault().post(new NetworkErrorEvent(action, error));
                    }
                }
        ) {
            @Override
            public byte[] getBody() {
                return gson.toJson(requestData).getBytes();
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json");
                return headers;
            }
        };
        // Set longer timeout
        request.setRetryPolicy(new DefaultRetryPolicy(15000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(request);
    }

    // region Start Game
    static Response.Listener<String> startGameListener = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            try {
                ActionResponse<GameInfo> gameInfo = gson.fromJson(response,
                        new TypeToken<ActionResponse<GameInfo>>(){}.getType());
                if (gameInfo.getData() == null) {
                    EventBus.getDefault().post(new ServerDataErrorEvent(Constants.ACTION_START, response, null));
                }
                EventBus.getDefault().post(new GameStartEvent(gameInfo));
            } catch (JsonSyntaxException ex) {
                Log.e(TAG, "PARSE SERVER DATA ERROR", ex);
                EventBus.getDefault().post(new ServerDataErrorEvent(Constants.ACTION_START, response, ex));
            }
        }
    };

    public static void startGame(StartGameAction action) {
        try {
            post(Constants.ACTION_START, action, startGameListener);
        } catch (AuthFailureError ex) {
            Log.e(TAG, "AUTH FAIL ERROR", ex);
            EventBus.getDefault().post(new NetworkErrorEvent(Constants.ACTION_START, ex));
        }
    }
    // endregion

    // region Give me a word
    static Response.Listener<String> nextWordListener = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            try {
                ActionResponse<WordInfo> wordInfo = gson.fromJson(response,
                        new TypeToken<ActionResponse<WordInfo>>(){}.getType());
                if (wordInfo.getData() == null) {
                    EventBus.getDefault().post(new ServerDataErrorEvent(Constants.ACTION_NEXT_WORD, response, null));
                }
                EventBus.getDefault().post(new WordInfoUpdateEvent(Constants.ACTION_NEXT_WORD, wordInfo));
            } catch (JsonSyntaxException ex) {
                Log.e(TAG, "PARSE SERVER DATA ERROR", ex);
                EventBus.getDefault().post(new ServerDataErrorEvent(Constants.ACTION_NEXT_WORD, response, ex));
            }
        }
    };

    public static void nextWord() {
        try {
            post(Constants.ACTION_NEXT_WORD,
                    new SessionRequestAction(Constants.ACTION_NEXT_WORD, App.get().getSession()),
                    nextWordListener);
        } catch (AuthFailureError ex) {
            Log.e(TAG, "AUTH FAIL ERROR", ex);
            EventBus.getDefault().post(new NetworkErrorEvent(Constants.ACTION_NEXT_WORD, ex));
        }
    }
    // endregion

    // region Make a guess
    static Response.Listener<String> guessWordListener = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            try {
                ActionResponse<WordInfo> wordInfo = gson.fromJson(response,
                        new TypeToken<ActionResponse<WordInfo>>(){}.getType());
                if (wordInfo.getData() == null) {
                    EventBus.getDefault().post(new ServerDataErrorEvent(Constants.ACTION_GUESS, response, null));
                }
                EventBus.getDefault().post(new WordInfoUpdateEvent(Constants.ACTION_GUESS, wordInfo));
            } catch (JsonSyntaxException ex) {
                Log.e(TAG, "PARSE SERVER DATA ERROR", ex);
                EventBus.getDefault().post(new ServerDataErrorEvent(Constants.ACTION_GUESS, response, ex));
            }
        }
    };

    public static void guessWord(GuessWordAction action) {
        try {
            post(Constants.ACTION_GUESS, action, guessWordListener);
        } catch (AuthFailureError ex) {
            Log.e(TAG, "AUTH FAIL ERROR", ex);
            EventBus.getDefault().post(new NetworkErrorEvent(Constants.ACTION_GUESS, ex));
        }
    }
    // endregion

    // region Refresh score
    static Response.Listener<String> getResultListener = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            try {
                ActionResponse<ScoreInfo> resultInfo = gson.fromJson(response,
                        new TypeToken<ActionResponse<ScoreInfo>>(){}.getType());
                if (resultInfo.getData() == null) {
                    EventBus.getDefault().post(new ServerDataErrorEvent(Constants.ACTION_GET_RESULT, response, null));
                }
                EventBus.getDefault().post(new ScoreInfoUpdateEvent(resultInfo));
            } catch (JsonSyntaxException ex) {
                Log.e(TAG, "PARSE SERVER DATA ERROR", ex);
                EventBus.getDefault().post(new ServerDataErrorEvent(Constants.ACTION_GET_RESULT, response, ex));
            }
        }
    };

    public static void getResult() {
        try {
            post(Constants.ACTION_GET_RESULT,
                    new SessionRequestAction(Constants.ACTION_GET_RESULT, App.get().getSession()),
                    getResultListener);
        } catch (AuthFailureError ex) {
            Log.e(TAG, "AUTH FAIL ERROR", ex);
            EventBus.getDefault().post(new NetworkErrorEvent(Constants.ACTION_GET_RESULT, ex));
        }
    }
    // endregion

    // region Submit
    static Response.Listener<String> submitListener = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            try {
                ActionResponse<SubmitResultInfo> submitInfo = gson.fromJson(response,
                        new TypeToken<ActionResponse<SubmitResultInfo>>(){}.getType());
                if (submitInfo.getData() == null) {
                    EventBus.getDefault().post(new ServerDataErrorEvent(Constants.ACTION_SUBMIT, response, null));
                }
                EventBus.getDefault().post(new ResultSubmitEvent(submitInfo));
            } catch (JsonSyntaxException ex) {
                Log.e(TAG, "PARSE SERVER DATA ERROR", ex);
                EventBus.getDefault().post(new ServerDataErrorEvent(Constants.ACTION_SUBMIT, response, ex));
            }
        }
    };

    public static void submit() {
        try {
            post(Constants.ACTION_SUBMIT,
                    new SessionRequestAction(Constants.ACTION_SUBMIT, App.get().getSession()),
                    submitListener);
        } catch (AuthFailureError ex) {
            Log.e(TAG, "AUTH FAIL ERROR", ex);
            EventBus.getDefault().post(new NetworkErrorEvent(Constants.ACTION_SUBMIT, ex));
        }
    }
    // endregion
}
