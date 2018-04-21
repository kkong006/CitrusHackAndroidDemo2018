package com.citrushack.lobdemo.sync;

import android.content.Context;

import com.citrushack.lobdemo.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class LobDemoClient {

    public void createAddress(String description, String address, String city,
                              String state, String zip, Context context, JsonHttpResponseHandler handler) {
        // TODO: Issue a POST request to https://api.lob.com/v1/addresses
        AsyncHttpClient client = new AsyncHttpClient();
        client.setBasicAuth(context.getString(R.string.test_key), context.getString(R.string.password));
        RequestParams params = new RequestParams();
        params.put("name", description);
        params.put("address_line1", address);
        params.put("address_city", city);
        params.put("address_state", state);
        params.put("address_zip", zip);
        client.post("https://api.lob.com/v1/addresses", params, handler);
    }

    public void getAddresses(int offset, int limit, Context context, JsonHttpResponseHandler handler) {
        // TODO: Issues a GET request to https://api.lob.com/v1/addresses
        AsyncHttpClient client = new AsyncHttpClient();
        client.setBasicAuth(context.getString(R.string.test_key), context.getString(R.string.password));
        RequestParams params = new RequestParams();
        params.put("offset", offset);
        params.put("limit", limit);
        client.get("https://api.lob.com/v1/addresses", params, handler);
    }
}
