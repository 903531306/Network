package com.ys.network.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;

import com.wang.avi.AVLoadingIndicatorView;
import com.ys.network.R;

public class LoadingDialog {

    private volatile static LoadingDialog diaLog;

    private LoadingDialog() {
    }

    public static LoadingDialog getInstance() {
        if (diaLog == null) {
            synchronized (LoadingDialog.class) {
                if (diaLog == null) {
                    diaLog = new LoadingDialog();
                }
            }
        }
        return diaLog;
    }


    public AlertDialog showDiaLog(final Context mContext) {
        // 创建对话框构建器
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext, R.style.DialogTheme);
        // 获取布局
        View view2 = View.inflate(mContext, R.layout.dialog_loading, null);
        // 获取布局中的控件
       final AVLoadingIndicatorView indicator=view2.findViewById(R.id.indicator);

        indicator.show();
        // 设置参数
        builder.setView(view2);
//        LinearLayout layout_dialog = (LinearLayout) view2.findViewById(R.id.layout_dialog);

        // 创建对话框
        final AlertDialog alertDialog = builder.create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setCancelable(true);
        alertDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                indicator.hide();
            }
        });

        return alertDialog;
//        alertDialog.show();
    }

    public interface DataCallBack{
        void dataCall(String str);
    }
}
