package com.ys.network.progress;

import android.content.Context;

import com.google.gson.JsonParseException;

import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.text.ParseException;

import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;

public class ProgressSubscriber<T> extends Subscriber<T> implements ProgressCancelListener  {
    private SubscriberOnResponseListenter mSubscriberOnResponseListenter;
    private ProgressDialogHandler mProgressDialogHandler;
    private boolean isShowProgress;

    public ProgressSubscriber(SubscriberOnResponseListenter mSubscriberOnResponseListenter, Context context, boolean isShowProgress) {
        this.mSubscriberOnResponseListenter = mSubscriberOnResponseListenter;
        this.isShowProgress = isShowProgress;
        mProgressDialogHandler = new ProgressDialogHandler(context, this, false);
    }

    /**
     * 开始订阅的时候显示加载框
     */
    @Override
    public void onStart() {
        if (isShowProgress)
            showProgressDialog();
    }

    @Override
    public void onCompleted() {
        dismissProgressDialog();
    }

    @Override
    public void onError(Throwable e) {
        String errorBean="";
        dismissProgressDialog();
//        BaseResultBean errorBean;
        //错误码要以服务器返回错误码为准。此处只是举例
        if (e instanceof HttpException) {             //HTTP 错误
            HttpException httpException = (HttpException) e;

            switch (httpException.code()) {
                case 401:
                    errorBean = "token过期或者已失效";
                    break;
                case 500:
                    errorBean = "服务器错误";
                    break;
//                case BaseResultBean.ERROR_CODE_FORBIDDEN:
//                    errorBean = new BaseResultBean(BaseResultBean.ERROR_CODE_FORBIDDEN, "但是拒绝执行它");
//                    break;
//                case BaseResultBean.ERROR_CODE_NOT_FOUND:
//                    errorBean = new BaseResultBean(BaseResultBean.ERROR_CODE_NOT_FOUND, "服务器异常，请稍后再试");
//                    break;
//                default:
//                    //其它均视为网络错误
//                    errorBean = new BaseResultBean(BaseResultBean.ERROR_CODE_FORBIDDEN, "网络错误");
//                    break;
            }
        } else if (e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof ParseException
                ||e instanceof IllegalStateException) {
            errorBean="解析错误";
//            errorBean = new BaseResultBean(BaseResultBean.ERROR_CODE_PARSE_JSON, "解析错误");
        } else if (e instanceof ConnectException || e instanceof SocketTimeoutException || e instanceof ConnectTimeoutException) {
            errorBean="网络连接失败，请检查是否联网";
//            errorBean = new BaseResultBean(BaseResultBean.ERROR_CODE_NETWORK, "网络连接失败，请检查是否联网");
        } else {
            errorBean="未知错误";
//            errorBean = new BaseResultBean(BaseResultBean.ERROR_CODE_UNKNOWN, "未知错误");
        }
        mSubscriberOnResponseListenter.error(errorBean);
    }
    //成功执行下一步
    @Override
    public void onNext(T t) {
        mSubscriberOnResponseListenter.next(t);
    }

    @Override
    public void onCancelProgress() {
        if (!this.isUnsubscribed()) {
            this.unsubscribe();
        }
    }
    //显示dialog
    private void showProgressDialog() {
        if (mProgressDialogHandler != null) {
            mProgressDialogHandler.obtainMessage(ProgressDialogHandler.SHOW_PROGRESS_DIALOG).sendToTarget();
        }
    }
    //隐藏dialog
    private void dismissProgressDialog() {
        if (mProgressDialogHandler != null) {
            mProgressDialogHandler.obtainMessage(ProgressDialogHandler.DISMISS_PROGRESS_DIALOG).sendToTarget();
        }
    }
}
