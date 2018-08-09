package com.ys.network.base;

import android.annotation.TargetApi;
import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.ys.network.BaseApplication;


public abstract class DataBindingActivity<B extends ViewDataBinding> extends AppCompatActivity {
    protected B mViewBinding = null;
    protected Activity mActivity;
    protected BaseApplication mApplication;

    @TargetApi(23)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = this;
        //所有Activity头部全屏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
        View rootView = getLayoutInflater().inflate(this.getLayoutId(), null, false);
        setContentView(rootView);

        mViewBinding = DataBindingUtil.bind(rootView);
        mApplication = (BaseApplication) getApplication();
        if(setStatusBarColor()){
            if (Build.VERSION.SDK_INT > 21) {
                getWindow().setStatusBarColor(Color.parseColor("#f05638"));
            }
        }


        initView();
        initData();
    }

    public abstract boolean setStatusBarColor();
    public abstract int getLayoutId();

    public abstract void initView();

    public abstract void initData();
}
