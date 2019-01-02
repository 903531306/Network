package com.ys.network.base;

import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;

import com.squareup.otto.Bus;
import com.ys.network.ActivityUtils;
import com.ys.network.bus.BusProvider;


public abstract class BaseActivity<P extends BasePresenter, B extends ViewDataBinding> extends DataBindingActivity<B> {

    protected P mPresenter;
    protected Bus bus;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = CreateUtil.getT(this, 0);
        mPresenter.attachModelView(this);
        ActivityUtils.activities.add(this);
        //注册otto
        bus = BusProvider.getInstance();
        if (bus != null) {
            bus.register(this);
        }
    }

    protected void remove() {
        if (ActivityUtils.activities != null && ActivityUtils.activities.size() > 0) {
            for (int i = 0; i < ActivityUtils.activities.size(); i++) {
                if (!ActivityUtils.activities.get(i).getClass().getName().equals("com.smart.logistics.MainActivity")) {
                    ActivityUtils.activities.get(i).finish();
                }
            }
        }
    }

    protected void removeActivity() {
        if (ActivityUtils.activities != null && ActivityUtils.activities.size() > 0) {
            for (int i = 0; i < ActivityUtils.activities.size(); i++) {
                ActivityUtils.activities.get(i).finish();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //注销监听订阅者
        if (bus != null) {
            bus.unregister(this);
        }
        //取消网络的订阅者
        if (mPresenter != null) {
            mPresenter.unSubscribe();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //取消网络的订阅者
            if (mPresenter != null) {
                mPresenter.unSubscribe();
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public abstract int getLayoutId();

    @Override
    public abstract void initView();

    @Override
    public abstract void initData();


}
