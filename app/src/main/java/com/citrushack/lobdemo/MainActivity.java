package com.citrushack.lobdemo;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;


public class MainActivity extends AppCompatActivity {

    private RecyclerView mRvAddresses;
//    private List<Address> mAddresses;
    private AddressAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;

    private boolean isLoading;
    private boolean isLastPage;

    private static final int PAGE_SIZE = 25;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton fab = findViewById(R.id.fabAdd);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Launch an intent to transition to AddAddressActivity
                Intent i = new Intent(getApplicationContext(), AddAddressActivity.class);
                startActivity(i);
            }
        });

        // Bind the recyclerview
        mRvAddresses = findViewById(R.id.rvAddresses);

        // Initialize address list
//        mAddresses = new ArrayList<>();

        // Create the adapter
        mAdapter = new AddressAdapter(this, new ArrayList<Address>());

        // Attach the adapter to the recyclerview
        mRvAddresses.setAdapter(mAdapter);

        // Set layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRvAddresses.setLayoutManager(mLayoutManager);

        // Set the onscrolllistener for pagination
        mRvAddresses.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int visibleItemCount = mLayoutManager.getChildCount();
                int totalItemCount = mLayoutManager.getItemCount();
                int firstVisibleItem = mLayoutManager.findFirstVisibleItemPosition();

                if(!isLoading && !isLastPage) {
                    if((visibleItemCount + firstVisibleItem) >= totalItemCount
                        && firstVisibleItem >= 0
                        && totalItemCount >= PAGE_SIZE) {
                        loadMoreItems();
                    }
                }
            }
        });
    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//        loadMoreItems();
//    }

//    @Override
//    protected void onPostResume() {
//        super.onPostResume();
//        loadMoreItems();
//    }

    @Override
    protected void onResume() {
        super.onResume();
        loadItems();
    }

    private void loadItems() {
        isLoading = false;
        isLastPage = false;

        mAdapter.clearAll();

        loadMoreItems();
    }

    private void loadMoreItems() {
        isLoading = true;

        AsyncHttpClient client = new AsyncHttpClient();
        client.setBasicAuth(getString(R.string.test_key), getString(R.string.password));
        RequestParams params = new RequestParams();
        params.put("offset", mLayoutManager.getItemCount());
        params.put("limit", PAGE_SIZE);

        client.get("https://api.lob.com/v1/addresses", params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                isLoading = false;
                Gson gson = new Gson();
                try {
                    Type type = new TypeToken<List<Address>>(){}.getType();
                    List<Address> newAddresses = gson.fromJson(response.getJSONArray("data").toString(), type);
                    mAdapter.addAll(newAddresses);
                    if(newAddresses.size() < PAGE_SIZE) {
                        isLastPage = true;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
            }
        });
    }
}
