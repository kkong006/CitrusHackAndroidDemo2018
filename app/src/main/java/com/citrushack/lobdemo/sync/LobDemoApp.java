package com.citrushack.lobdemo.sync;

import android.app.Application;

public class LobDemoApp extends Application {

    private static LobDemoClient lobDemoClient;

    public static LobDemoClient getLobDemoClient() {
        if(lobDemoClient == null) {
            lobDemoClient = new LobDemoClient();
        }
        return lobDemoClient;
    }
}
