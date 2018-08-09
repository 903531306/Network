package com.ys.network.network;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class NetworkInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        //一、无论有无网路都添加缓存。
        Request request = chain.request();
        Response response = chain.proceed(request);
        int maxAge = 60;

        return response.newBuilder()
                .removeHeader("Pragma")
                .removeHeader("Cache-Control")
                .addHeader("name","shone")
                .header("Cache-Control", "public, max-age=" + maxAge)
                .build();
    }
}
