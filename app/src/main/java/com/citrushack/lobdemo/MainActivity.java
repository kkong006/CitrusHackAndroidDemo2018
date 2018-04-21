package com.citrushack.lobdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.json.JSONObject;

import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    // TODO: Define a page size

    // TODO: Bind the recyclerview to a member variable

    // TODO: Declare the adapter for the recyclerview

    // TODO: Declare the layout manager for the recyclerview

    // TODO: Declare booleans isLoading and isLastPage for pagination

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        // TODO: Create the adapter

        // TODO: Attach the adapter to the recyclerview

        // TODO: Set the layout manager

        // TODO: Set the OnScroll Listener for pagination
    }

    // TODO: Add an OnClick listener to the add button


    @Override
    protected void onResume() {
        super.onResume();

        // Get addresses and load them into the recyclerview
        loadAddresses();
    }

    private void loadAddresses() {
        // TODO: Reset the loading state and current page

        // Fetch items starting from the first
        loadMoreAddresses();
    }

    private void loadMoreAddresses() {
        // TODO: set the loading state

        // TODO: Issue a GET addresses request using the API Client
    }

    private void parseAddresses(JSONObject addresses) {
        // TODO: Parse the response using the GSON library

    }

}
