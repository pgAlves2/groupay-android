package com.alves.pedro.groupay.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonUtils {

    private static Gson mGson;

    public static Gson getInstance() {
        if (mGson == null)
            mGson = new GsonBuilder().create();
        return mGson;
    }
}
