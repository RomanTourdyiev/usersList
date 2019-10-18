package com.example.userslist.util;

import android.os.AsyncTask;

import java.util.Locale;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

import static com.example.userslist.Config.HOST;

/**
 * Created by Tourdyiev Roman on 2019-10-18.
 */
public class APIHelper {

    private Callback callback;
    private static OkHttpClient client;

    private static APIHelper instance = new APIHelper();

    public static APIHelper getInstance() {
        client = new OkHttpClient();
        return instance;
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    public void getUsers(final int limit, final int offset) {
        final String url = String.format(Locale.ENGLISH, "%s?limit=%d&offset=%d", HOST, limit, offset);
        int d= 0;
        d++;
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                Request request = new Request.Builder()
                        .url(url)
                        .get()
                        .build();

                if (callback != null) {
                    client.newCall(request).enqueue(callback);
                }
            }
        });
    }


}
