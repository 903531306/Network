package demo.image.com.demonetwork.presenter.impl;

import android.app.Activity;

import org.json.JSONObject;

import java.io.Serializable;

public interface TestPresenter {
    void getMovieListData(int start, int count, Activity activity);
    void area(JSONObject jsonObject,Activity activity);
    interface TestView{
        void succ(Serializable serializable);
        void error(String str);
    }
}
