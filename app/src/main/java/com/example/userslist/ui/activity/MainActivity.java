package com.example.userslist.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.userslist.R;
import com.example.userslist.model.User;
import com.example.userslist.ui.adapter.UsersAdapter;
import com.example.userslist.util.APIHelper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static androidx.recyclerview.widget.RecyclerView.*;

public class MainActivity extends AppCompatActivity implements Callback {

    private UsersAdapter usersadapter;
    private APIHelper apiHelper;
    private Gson gson;

    private RecyclerView recyclerView;

    private List<User> list = new ArrayList<>();
    private Type listType = new TypeToken<List<User>>() {}.getType();

    // API callbacks
    @Override
    public void onFailure(@NotNull Call call, @NotNull IOException e) {

    }

    @Override
    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
        try {
            String responce = response.body().string();
            JSONObject jsonObject = new JSONObject(responce);

            if (jsonObject.has("data")) {
                if (jsonObject.getJSONObject("data").has("users")) {
                    JSONArray jsonArray = jsonObject.getJSONObject("data").getJSONArray("users");
                    if (jsonArray.length()!=0) {
                        List<User> users = gson.fromJson(jsonArray.toString(), listType);
                        list.addAll(users);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                usersadapter.notifyDataSetChanged();
                            }
                        });
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                usersadapter.hideLoadingItem();
                            }
                        });
                    }
                }
            }


        } catch (JSONException je) {
            je.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gson = new Gson();
        apiHelper = APIHelper.getInstance();
        apiHelper.setCallback(this);
        findViews();
        initViews();
    }

    private void findViews() {
        recyclerView = findViewById(R.id.recycler_view);
    }

    private void initViews() {
        list.clear();
        // users list
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        usersadapter = new UsersAdapter(list);
        recyclerView.setAdapter(usersadapter);
    }
}
