package com.citrushack.lobdemo.sync;

import android.content.Context;

import com.citrushack.lobdemo.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

public class LobDemoClient {

    public void createAddress(String description, String address, String city,
                              String state, String zip, Context context, JsonHttpResponseHandler handler) {
        // TODO: Issue a POST request to https://api.lob.com/v1/addresses
    }

    public void getAddresses(int offset, int limit, Context context, JsonHttpResponseHandler handler) {
        // TODO: Issues a GET request to https://api.lob.com/v1/addresses
    }
}
