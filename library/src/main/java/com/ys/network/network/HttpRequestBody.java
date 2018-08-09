package com.ys.network.network;

import org.json.JSONObject;

import okhttp3.RequestBody;

public class HttpRequestBody {
    public static RequestBody getRequestBody(JSONObject jsonObject){
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),(jsonObject.toString()));
        return body;
    }

}
