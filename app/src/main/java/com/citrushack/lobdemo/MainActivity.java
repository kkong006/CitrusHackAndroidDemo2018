package com.citrushack.lobdemo;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.citrushack.lobdemo.sync.LobDemoApp;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {

    // TODO: Define a page size
    private static final int PAGE_SIZE = 25;

    // TODO: Bind the recyclerview to a member variable
    @BindView(R.id.rvAddresses)
    RecyclerView mRvAddresses;

    // TODO: Declare the adapter for the recyclerview
    private AddressAdapter mAdapter;

    // TODO: Declare the layout manager for the recyclerview
    private LinearLayoutManager mLayoutManager;

    // TODO: Declare booleans isLoading and isLastPage for pagination
    private boolean isLoading;
    private boolean isLastPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        // TODO: Create the adapter
        mAdapter = new AddressAdapter(this, new ArrayList<Address>());

        // TODO: Attach the adapter to the recyclerview
        mRvAddresses.setAdapter(mAdapter);

        // TODO: Set the layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRvAddresses.setLayoutManager(mLayoutManager);

//        FloatingActionButton button = findViewById(R.id.fabAdd);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                Intent i = new
//            }
//        });

        // TODO: Set the OnScroll Listener for pagination
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
                    if(visibleItemCount + firstVisibleItem >= totalItemCount
                            && firstVisibleItem >= 0
                            && totalItemCount >= PAGE_SIZE) {
                        loadMoreAddresses();
                    }
                }
            }
        });
    }

    // TODO: Add an OnClick listener to the add button
    @OnClick(R.id.fabAdd)
    void onClickAdd() {
        Intent addAddressIntent = new Intent(this, AddAddressActivity.class);
        startActivity(addAddressIntent);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Get addresses and load them into the recyclerview
        loadAddresses();
    }

    private void loadAddresses() {
        // TODO: Reset the loading state and current page
        isLoading = false;
        isLastPage = false;
        mAdapter.clearAll();

        // Fetch items starting from the first
        loadMoreAddresses();
    }

    private void loadMoreAddresses() {
        // TODO: set the loading state
        if(!isLastPage) {
            isLoading = true;
            LobDemoApp.getLobDemoClient().getAddresses(mLayoutManager.getItemCount(), PAGE_SIZE, this, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    isLoading = false;

                    parseAddresses(response);
                }
            });
        }
        // TODO: Issue a GET addresses request using the API Client
    }

    private void parseAddresses(JSONObject addresses) {
        // TODO: Parse the response using the GSON library
        try {
            Gson gson = new Gson();
            Type type = new TypeToken<List<Address>>(){}.getType();
            List<Address> newAddresses = gson.fromJson(addresses.getJSONArray("data").toString(), type);
            mAdapter.addAll(newAddresses);
            if(newAddresses.size() < PAGE_SIZE) {
                isLastPage = true;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}
