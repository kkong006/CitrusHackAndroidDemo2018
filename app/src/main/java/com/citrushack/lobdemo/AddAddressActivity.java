package com.citrushack.lobdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import com.citrushack.lobdemo.sync.LobDemoApp;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cz.msebera.android.httpclient.Header;

public class AddAddressActivity extends AppCompatActivity {

    // TODO: Bind the view elements to variables
    @BindView(R.id.etDescription) EditText mEtDescription;
    @BindView(R.id.etAddress) EditText mEtAddress;
    @BindView(R.id.etCity) EditText mEtCity;
    @BindView(R.id.etState) EditText mEtState;
    @BindView(R.id.etZip) EditText mEtZip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);
        ButterKnife.bind(this);

    }

    // TODO: Add an OnClick listener to the submit button that adds a new address
    @OnClick(R.id.btSubmit)
    void onClickSubmit() {
        String description = mEtDescription.getText().toString();
        String address = mEtAddress.getText().toString();
        String city = mEtCity.getText().toString();
        String state = mEtState.getText().toString();
        String zip = mEtZip.getText().toString();
        LobDemoApp.getLobDemoClient().createAddress(description, address, city, state, zip, this, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                finish();
            }
        });
    }
}
