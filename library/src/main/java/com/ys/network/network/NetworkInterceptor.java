package com.ys.network.network;

import com.ys.network.UserInfo;

import java.io.IOException;

import io.paperdb.Paper;
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
        UserInfo userInfo = null;
        if (Paper.book().read("userInfo") != null) {
            try {
                userInfo = Paper.book().read("userInfo");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return response.newBuilder()
                    .removeHeader("Pragma")
                    .removeHeader("Cache-Control")
                    .addHeader("token",userInfo.getToken())
                    .header("Cache-Control", "public, max-age=" + maxAge)
                    .build();
        }else{
            return response.newBuilder()
                    .removeHeader("Pragma")
                    .removeHeader("Cache-Control")
                    .header("Cache-Control", "public, max-age=" + maxAge)
                    .build();
        }



    }
}
