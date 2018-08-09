package com.ys.network.network;

import android.content.Context;
import android.os.Environment;
import android.util.Log;


import com.ys.network.BaseApplication;
import com.ys.network.utils.LogUtils;
import com.ys.network.utils.NetworkUtils;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class RetrofitManager {
    private static RetrofitManager instance;
    public static int MAXSTALE = 60 * 60 * 24 * 28; // 无网络时，设置超时为4周
    public static int CONNECT_OUTTIME = 10; // 链接超时时间 unit:S
    public static int READ_OUTTIME = 20; // 读取数据超时时间 unit:S
    public static int WRITE_OUTTIME = 20; // 写入超时时间 unit:S
    public static long CACHE_SIZE = 1024*1024*50; // 缓存大小 50M
    private final OkHttpClient mOkHttpClient;
    private final Retrofit mRetrofit;

    /**
     * 创建单例
     */
    public static RetrofitManager getInstace() {
        if (instance == null) {
            synchronized (RetrofitManager.class) {
                instance = new RetrofitManager();
            }
        }
        return instance;
    }

    /**
     * 获取retrofit
     */
    public Retrofit getRetrofit() {
        return mRetrofit;
    }

    /**
     * 创建服务类
     * @return
     */
    public <T> T create(Class<T> service) {
        return mRetrofit.create(service);
    }

    /**
     * rx订阅
     */
    public  <T> void toSubscribe(Observable<T> o, Subscriber<T> s) {

        o.subscribeOn(Schedulers.io())

                .unsubscribeOn(Schedulers.io())

                .observeOn(AndroidSchedulers.mainThread())

                .subscribe(s);

    }
    private Context mContext;
    public void setContext(Context context){
        this.mContext=context;
    }
    static HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
        @Override
        public void log(String message) {
            //打印retrofit日志
            Log.i("RetrofitLog", "retrofitBack = " + message);
        }
    });
    private RetrofitManager() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        //打印日志
        if(LogUtils.LOG_FLAG){
            // https://drakeet.me/retrofit-2-0-okhttp-3-0-config
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(loggingInterceptor);
        }
        //设置缓存
        File cacheFile=new File(Environment.getExternalStorageDirectory().getAbsolutePath());
        Cache cache=new Cache(cacheFile,CACHE_SIZE);
        Interceptor cacheInterceptor=new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                if (!NetworkUtils.isConnected(BaseApplication.getInstance().getApplicationContext())) {
                    request = request.newBuilder()
                            .cacheControl(CacheControl.FORCE_CACHE)
                            .build();
                }
                Response response = chain.proceed(request);
                if (NetworkUtils.isConnected(BaseApplication.getInstance().getApplicationContext())) {
                    String cacheControl = request.cacheControl().toString();
                    return response.newBuilder()
                            .header("Cache-Control", cacheControl)
                            .removeHeader("Pragma")
                            .build();

                } else {
                    return response.newBuilder()
                            .header("Cache-Control", "public, only-if-cached, max-stale=2419200")
                            .removeHeader("Pragma")
                            .build();
                }
            }
        };
        //设置缓存
        builder.addNetworkInterceptor(cacheInterceptor);
        builder.addNetworkInterceptor(new NetworkInterceptor());//这里大家一定要注意了是addNetworkOnterceptor别搞错了啊。
        builder.addInterceptor(new NetworkInterceptor());
        builder.cache(cache);
        //设置超时
        builder.connectTimeout(CONNECT_OUTTIME, TimeUnit.SECONDS);
        builder.readTimeout(READ_OUTTIME, TimeUnit.SECONDS);
        builder.writeTimeout(WRITE_OUTTIME, TimeUnit.SECONDS);
        //错误重连
        builder.retryOnConnectionFailure(true);
        mOkHttpClient = builder.build();
        mRetrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl("http://api.dgweidao.com/api/v1/")
                .client(mOkHttpClient)
                .build();

    }

}
