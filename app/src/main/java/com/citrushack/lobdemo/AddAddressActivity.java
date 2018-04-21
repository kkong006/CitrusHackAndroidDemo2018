package com.citrushack.lobdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import butterknife.ButterKnife;

public class AddAddressActivity extends AppCompatActivity {

    // TODO: Bind the view elements to variables

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);
        ButterKnife.bind(this);

    }

    // TODO: Add an OnClick listener to the submit button that adds a new address
}
