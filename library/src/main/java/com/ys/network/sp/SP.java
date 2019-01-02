package com.ys.network.sp;


import android.content.Context;

/**
 * Created by Thisfeng on 2017/3/9 0009 21:54
 * 项目中专门用来管理本地用户数据 和公共的数据的 SharedPreferences
 */

public class SP {

    private static ShareStorage accountStorage;
    private static ShareStorage userStorage;
    private static ShareStorage publicStorage;

    public static final String server = "server";
    private static final String accounts = "accounts";
    public static final String user = "user";
    private static final String publicSP = "publicSP";

    public static ShareStorage getAccount(Context activity) {
        if (accountStorage == null) {
            accountStorage = new ShareStorage(activity, accounts);
        }
        return accountStorage;
    }





    /**
     * 若要清空用户数据
     */
    public static void clearAllUserInfo() {
//        SP.getUser().put(user, null);
//        SP.getUser().put(userInfo, null);
    }

}
