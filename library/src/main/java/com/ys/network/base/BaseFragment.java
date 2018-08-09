package com.ys.network.base;

import android.annotation.SuppressLint;
import android.databinding.ViewDataBinding;
import android.support.v4.app.Fragment;

import java.util.List;

public abstract class BaseFragment<P extends BasePresenter, B extends ViewDataBinding> extends DataBindingFragment<B> {
    protected P mPresenter;

    @Override
    public abstract void initView();

    @Override
    public abstract void initData();

    @Override
    public void initPresenter(){
        mPresenter = CreateUtil.getT(this, 0);
        mPresenter.attachModelView(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(bus!=null){
            bus.unregister(this);
        }
        if(mPresenter!=null){
            mPresenter.unSubscribe();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        @SuppressLint("RestrictedApi") List<Fragment> fragments = getChildFragmentManager().getFragments();
        if (fragments != null) {
            for (Fragment fragment : fragments) {
                if (fragment != null) {
                    fragment.onRequestPermissionsResult(requestCode, permissions, grantResults);
                }
            }
        }
    }
}
