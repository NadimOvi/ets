package com.theah64.ets.utils;

import android.app.Application;

/**
 * Created by theapache64 on 19/11/16.
 */

public class App extends Application {

    public static final boolean IS_DEBUG_MODE = true;

    public static String getCompanyCode() {
        //TODO: read from external json file
        return "xyzComp";
    }
}