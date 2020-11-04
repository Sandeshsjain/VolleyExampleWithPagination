package com.example.userlist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    RecyclerView recycler_view;
    public static final String API = "https://reqres.in/api/users?page=";
    public static final String TAG = "MainActivity";
    public RequestQueue mRequestQue;
    int count = 1;
    DataAdapter adapter;
    List<DataBean> list;
    public int maxPage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recycler_view = findViewById(R.id.recycler_view);
        list = new ArrayList<>();
        mRequestQue = Volley.newRequestQueue(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recycler_view.setLayoutManager(linearLayoutManager);
        getData(count);
        adapter = new DataAdapter(this,list);
        recycler_view.setAdapter(adapter);
        recycler_view.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                if(isLastItemDisplaying(recycler_view)){
                    count++;
                    Log.d(TAG, "onScrolled: "+count);
                    getData(count);
                }
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }

    private void getData(int pageCount) {
        StringRequest request = new StringRequest(Request.Method.GET, API+pageCount, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "onResponse: "+response);
                JSONObject rootObject = null;
                try {
                    rootObject = new JSONObject(response);
                    JSONArray data = rootObject.getJSONArray("data");
                    for(int i = 0;i<data.length();i++){
                        DataBean dataBean = new DataBean();
                        JSONObject jsonObject = data.getJSONObject(i);
                        Log.d(TAG, "data: "+jsonObject);
                        dataBean.setAvatar(jsonObject.getString("avatar"));
                        dataBean.setEmail(jsonObject.getString("email"));
                        dataBean.setFirst_name(jsonObject.getString("first_name"));
                        dataBean.setLast_name(jsonObject.getString("last_name"));
                        dataBean.setId(jsonObject.getString("id"));
                        list.add(dataBean);
                    }
                    adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onErrorResponse: "+error);
            }
        });
        mRequestQue.add(request);
    }
    public static boolean isLastItemDisplaying(RecyclerView recyclerView) {
        if (recyclerView.getAdapter().getItemCount() != 0) {
            int lastVisibleItemPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastCompletelyVisibleItemPosition();
            return lastVisibleItemPosition != RecyclerView.NO_POSITION && lastVisibleItemPosition == recyclerView.getAdapter().getItemCount() - 1;
        }
        return false;
    }
}