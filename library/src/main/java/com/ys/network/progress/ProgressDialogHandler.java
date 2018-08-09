package com.ys.network.progress;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;

import com.ys.network.dialog.LoadingDialog;


public class ProgressDialogHandler extends Handler {
    public static final int SHOW_PROGRESS_DIALOG = 1;
    public static final int DISMISS_PROGRESS_DIALOG = 2;
    private Context context;
    private boolean cancelable;
    private ProgressCancelListener mProgressCancelListener;

    private AlertDialog loadingDialog;

    public ProgressDialogHandler(Context context, ProgressCancelListener mProgressCancelListener, boolean cancelable) {
        super();
        this.context = context;
        this.mProgressCancelListener = mProgressCancelListener;
        this.cancelable = cancelable;
    }
    //显示dialog
    private void initProgressDialog() {
        if (loadingDialog == null) {
            loadingDialog = LoadingDialog.getInstance().showDiaLog(context);
            loadingDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialogInterface) {
                    mProgressCancelListener.onCancelProgress();
                }
            });
            if (!loadingDialog.isShowing()) {
                loadingDialog.show();
            }
        }
    }
    //隐藏dialog
    private void dismissProgressDialog() {
        if (loadingDialog != null) {
            loadingDialog.dismiss();
        }
    }

    @Override
    public void handleMessage(Message msg) {
        switch (msg.what) {

            case SHOW_PROGRESS_DIALOG:
                initProgressDialog();
                break;

            case DISMISS_PROGRESS_DIALOG:
                dismissProgressDialog();
                break;

        }
    }
}
