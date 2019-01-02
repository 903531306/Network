package com.ys.network.network;

import android.app.Activity;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import com.ys.network.UserInfo;
import com.ys.network.converter.JsonConverterFactory;
import com.ys.network.des.Des3;
import com.ys.network.sp.SP;
import com.ys.network.utils.LogUtils;
import com.ys.network.utils.NetworkUtils;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import io.paperdb.Paper;
import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class RetrofitManager {
    private static RetrofitManager instance;
    public static int MAXSTALE = 60 * 60 * 24 * 28; // 无网络时，设置超时为4周
    public static int CONNECT_OUTTIME = 10; // 链接超时时间 unit:S
    public static int READ_OUTTIME = 40; // 读取数据超时时间 unit:S
    public static int WRITE_OUTTIME = 40; // 写入超时时间 unit:S
    public static long CACHE_SIZE = 1024*1024*50; // 缓存大小 50M
    private final OkHttpClient mOkHttpClient;
    private final Retrofit mRetrofit;
    public static String token="";
//    private String baseUrl="http://192.168.31.207:8081/eascs/";
//    private String baseUrl="http://192.168.1.116:8081/eascs/";
    public static String baseUrl="http://58.250.29.163/eascs/";

    /**
     * 创建单例
     */
    public static RetrofitManager getInstace(Activity activity) {
        mContext=activity;
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
    public static Activity mContext;

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
                if (!NetworkUtils.isConnected(mContext)) {
                    request = request.newBuilder()
                            .cacheControl(CacheControl.FORCE_CACHE)
                            .build();
                }
                Response response = chain.proceed(request);
                if (NetworkUtils.isConnected(mContext)) {
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
//        builder.addNetworkInterceptor(cacheInterceptor);
        builder.addNetworkInterceptor(tokenInterceptor);//这里大家一定要注意了是addNetworkOnterceptor别搞错了啊。
//        builder.addInterceptor(new NetworkInterceptor());
        builder.cache(cache);
        //设置超时
        builder.connectTimeout(CONNECT_OUTTIME, TimeUnit.SECONDS);
        builder.readTimeout(READ_OUTTIME, TimeUnit.SECONDS);
        builder.writeTimeout(WRITE_OUTTIME, TimeUnit.SECONDS);
        //错误重连
        builder.retryOnConnectionFailure(true);
        mOkHttpClient = builder.build();
        mRetrofit = new Retrofit.Builder()
                .baseUrl(TextUtils.isEmpty(SP.getAccount(mContext).getString(SP.server))?baseUrl:SP.getAccount(mContext).getString(SP.server))
                .client(mOkHttpClient)
                //增加返回值为String的支持
//                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(JsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

    }

    private Interceptor tokenInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request originalRequest = chain.request();

//            UserInfo userInfo = null;
//            if (Paper.book().read("userInfo") != null) {
//                try {
//                    userInfo = Paper.book().read("userInfo");
//                    Log.e("token", userInfo!=null?userInfo.getToken()+"|"+userInfo.getId():"");
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//
//            }

            Request authorised = null;
            try {
                authorised = originalRequest.newBuilder()
//                        .header("token", userInfo!=null?userInfo.getToken():token)
//                        .header("sign", Des3.encode("token"+(userInfo!=null?userInfo.getToken():token)+"timeStamp"+System.currentTimeMillis()))
                        .build();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return chain.proceed(authorised);

            // NOTE: 2017/10/31 修正"null"值  ，如果token为null,则传入""

        }
    };

}
