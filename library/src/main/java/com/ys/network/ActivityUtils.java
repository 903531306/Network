package com.ys.network;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

public class ActivityUtils {
    //在自己内部定义自己的一个实例，只供内部调用
    private static final ActivityUtils instance = new ActivityUtils();

    public static List<Activity> activities = new ArrayList<>();
}
