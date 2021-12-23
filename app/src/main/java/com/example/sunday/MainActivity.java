package com.example.sunday;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private RecyclerView chatsRV;
    private EditText userMsgEdt;
    private FloatingActionButton sendMsgFAB;
    private final String BOT_KEY="bot";
    private final String USER_KEY ="user";
    private ArrayList<ChatsModal> chatsModalArrayList;
    private ChatRVAdapter chatRVAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        chatsRV = findViewById(R.id.idRvchats);
        sendMsgFAB = findViewById(R.id.idFABSend);
        userMsgEdt = findViewById(R.id.idEdtMsg);
        chatsModalArrayList = new ArrayList<>();
        chatRVAdapter = new ChatRVAdapter(chatsModalArrayList, this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this, RecyclerView.VERTICAL, false);
        chatsRV.setLayoutManager(linearLayoutManager);
        chatsRV.setAdapter(chatRVAdapter);

        sendMsgFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userMsgEdt.getText().toString().isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please enter your message..", Toast.LENGTH_SHORT).show();
                    return;
                }
                sendMessage(userMsgEdt.getText().toString());
                userMsgEdt.setText("");
            }
        });


    }
    private void sendMessage(String message) {
        chatsModalArrayList.add(new ChatsModal(message,USER_KEY));
        chatRVAdapter.notifyDataSetChanged();
        String url = "https://api.brainshop.ai/get?bid=161209&key=S1qkRImwVjnEotxB&uid=[uid]&msg="+message;
        String BASE_URL = "https://api.brainshop.ai/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);
        Call<MsgModal> call = retrofitAPI.getMessages(url);
        chatRVAdapter.notifyDataSetChanged();
        call.enqueue(new Callback<MsgModal>() {

            @Override
            public void onResponse(Call<MsgModal> call, Response<MsgModal> response) {
                if(response.isSuccessful()){
                    Toast.makeText(MainActivity.this, "Got response", Toast.LENGTH_SHORT).show();
                    MsgModal modal = response.body();
                    chatsModalArrayList.add(new ChatsModal(modal.getcnt(),BOT_KEY));
                    chatRVAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<MsgModal> call, Throwable t) {
                Toast.makeText(MainActivity.this, "NO response", Toast.LENGTH_SHORT).show();
                chatsModalArrayList.add(new ChatsModal("Can you please revert",BOT_KEY));
              chatRVAdapter.notifyDataSetChanged();
            }
        });
    }
}




/*
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        chatsRV = findViewById(R.id.idRvchats);
        userMsgEdt = findViewById(R.id.idEdtMsg);
        sendMsgFAB = findViewById(R.id.idFABSend);
        chatsModalArrayList = new ArrayList<>();

        chatRVAdapter = new ChatRVAdapter(chatsModalArrayList, this);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        chatsRV.setLayoutManager(manager);

        chatsRV.setAdapter(chatRVAdapter);

        sendMsgFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (userMsgEdt.getText().toString().isEmpty()) {

                    Toast.makeText(MainActivity.this, "enter msg mainAct", Toast.LENGTH_SHORT).show();
                    return;
                }
                getResponse(userMsgEdt.getText().toString());
                userMsgEdt.setText("");
            }
        });
    }

    //calling API

    private void getResponse(String message){

        chatsModalArrayList.add(new ChatsModal(message, USER_KEY));
        chatRVAdapter.notifyDataSetChanged();
        String url="http://api.brainshop.ai/get?bid=154860&key=a0qeHISuBOho4Bpr&uid=mashape&msg=" + message;

        String BASE_URL = "http://api.brainshop.ai/";
        Retrofit retrofit=new Retrofit.Builder()
                 .baseUrl(BASE_URL)
                 .addConverterFactory(GsonConverterFactory.create())
                .build();





        RetrofitAPI retrofitAPI= retrofit.create(RetrofitAPI.class);
        Call<MsgModal> call= retrofitAPI.getMessage(url);
       call.enqueue(new Callback<MsgModal>() {
           @Override
           public void onResponse(Call<MsgModal> call, Response<MsgModal> response) {
               if (response.isSuccessful()){
                   MsgModal modal=response.body();
                   chatsModalArrayList.add(new ChatsModal(modal.getCnt(),BOT_KEY));
                   chatRVAdapter.notifyDataSetChanged();
               }

           }



           @Override
           public void onFailure(Call<MsgModal> call, Throwable t) {
               chatsModalArrayList.add(new ChatsModal("Bot not responding",BOT_KEY));
               chatRVAdapter.notifyDataSetChanged();

           }
       });


    }

}

 */