package demo.image.com.demonetwork.presenter.impl;

import android.app.Activity;

import com.ys.network.BaseApplication;
import com.ys.network.base.BasePresenter;
import com.ys.network.base.BaseView;
import com.ys.network.network.HttpRequestBody;
import com.ys.network.network.RetrofitManager;
import com.ys.network.progress.HttpResultFunc;
import com.ys.network.progress.ProgressSubscriber;
import com.ys.network.progress.SubscriberOnResponseListenter;

import org.json.JSONException;
import org.json.JSONObject;

import demo.image.com.demonetwork.ApiService;
import demo.image.com.demonetwork.entity.PublicEntity;
import okhttp3.RequestBody;
import rx.Subscriber;
import rx.Subscription;


public class TestPresenterImpl extends BasePresenter<TestPresenter.TestView> implements TestPresenter {
    @Override
    public void getMovieListData(int start, int count, Activity activity) {
        final TestView baseView = getView();
        //获取数据
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("pictureType", "0,5,6,7");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = HttpRequestBody.getRequestBody(jsonObject);
        rx.Observable<PublicEntity> observable = RetrofitManager.getInstace(activity).create(ApiService.class).getMovieListData(body).map((new HttpResultFunc<PublicEntity>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<PublicEntity>() {
            @Override
            public void next(PublicEntity testBean) {
                baseView.succ(testBean);
            }

            @Override
            public void error(String errResponse) {
                baseView.error(errResponse);
            }
        }, activity, false);
        RetrofitManager.getInstace(activity).toSubscribe(observable, (Subscriber) rxSubscription);
        addSubscrebe(rxSubscription);
    }

    @Override
    public void area(JSONObject jsonObject, Activity activity) {
        final TestView baseView = getView();
        RequestBody body = HttpRequestBody.getRequestBody(jsonObject);
        rx.Observable<PublicEntity> observable = RetrofitManager.getInstace(activity).create(ApiService.class).area(body).map((new HttpResultFunc<PublicEntity>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<PublicEntity>() {
            @Override
            public void next(PublicEntity testBean) {
                baseView.succ(testBean);
            }

            @Override
            public void error(String errResponse) {
                baseView.error(errResponse);
            }
        }, activity, false);
        RetrofitManager.getInstace(activity).toSubscribe(observable, (Subscriber) rxSubscription);
        addSubscrebe(rxSubscription);
    }


}
