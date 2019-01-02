package com.ys.network.converter;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.ys.network.des.Des3;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.Charset;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;

public class JsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {


    private final Gson gson;
    private final TypeAdapter<T> adapter;
    JsonResponseBodyConverter(Gson gson, TypeAdapter<T> adapter) {
        this.gson = gson;		this.adapter = adapter;	}
    @Override
    public T convert(ResponseBody value) throws IOException {
        String string = value.string();
        Log.e("RetrofitLog", "#解密前#" + string);
        try {
            string = Des3.decode(string);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.e("RetrofitLog", "#解密后#" + string);
        return adapter.fromJson(string);	}


}
