package com.ys.network.base;

import android.view.View;

import java.util.List;


/**
 * Created by ASUS on 2017/9/19.
 */

public interface MyItemClickListener<T> {
    void onItemClickListener(View view, int postion, List<T> ts);
    void onLongClickListener(View view, int postion);
}
