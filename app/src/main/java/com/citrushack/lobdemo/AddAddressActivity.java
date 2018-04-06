package com.citrushack.lobdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class AddAddressActivity extends AppCompatActivity {

    private EditText mEtDescription;
    private EditText mEtAddress;
    private EditText mEtCity;
    private EditText mEtState;
    private EditText mEtZip;

    private String description;
    private String address;
    private String city;
    private String state;
    private String zip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);

        mEtDescription = findViewById(R.id.etDescription);
        mEtAddress = findViewById(R.id.etAddress);
        mEtCity = findViewById(R.id.etCity);
        mEtState = findViewById(R.id.etState);
        mEtZip = findViewById(R.id.etZip);

        Button btSubmit = findViewById(R.id.btSubmit);
        btSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get the text the user entered
                description = mEtDescription.getText().toString();
                address = mEtAddress.getText().toString();
                city = mEtCity.getText().toString();
                state = mEtState.getText().toString();
                zip = mEtZip.getText().toString();

                // Validate the address
                checkAddress();
            }
        });
    }

    private void checkAddress() {

        // Issue a POST us_verifications request to validate the address
        // NOTE: Always returns undeliverable with a test key
        AsyncHttpClient client = new AsyncHttpClient();
        client.setBasicAuth(getString(R.string.test_key), getString(R.string.password));
        RequestParams params = new RequestParams();
        params.put("primary_line", address);
        params.put("city", city);
        params.put("state", state);
        params.put("zip_code", zip);
        client.post("https://api.lob.com/v1/us_verifications", params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    // If the response says the address is deliverable, alert the user
                    if(response.get("deliverability") == "deliverable") {
                        Toast.makeText(getApplicationContext(), "Verified address!", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Invalid address!", Toast.LENGTH_LONG).show();
                    }
                    // If the address is valid, add it to the address book
                    createAddress();
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

    private void createAddress() {

        // Issue a POST Addresses request
        AsyncHttpClient client = new AsyncHttpClient();
        client.setBasicAuth(getString(R.string.test_key), getString(R.string.password));
        RequestParams params = new RequestParams();
        params.put("name", description);
        params.put("address_line1", address);
        params.put("address_city", city);
        params.put("address_state", state);
        params.put("address_zip", zip);
        client.post("https://api.lob.com/v1/addresses", params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // If it was successfully executed, return to MainActivity
                finish();
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
