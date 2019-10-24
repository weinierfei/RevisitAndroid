package com.dragon.revisitandroid;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Scroller;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Description:
 *
 * @author guoyongping
 * @date 2019-08-31 10:42
 */
public class Test {


    private static Handler mHandler = new Handler();

    public static void main(String[] args) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                Log.d("11", "---");
            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {

            }
        }).start();


        String url = "";
        Request request = new Request.Builder()
                .get()
                .url(url)
                .build();
        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        Call call = builder.build().newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                // 在子线程，如果想更新界面必须切换到主线程
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                // 在子线程，如果想更新界面必须切换到主线程
            }
        });

    }


}

class TestView extends View{

    private Scroller mScroller;
    public TestView(Context context) {
        super(context);
        mScroller = new Scroller(context);
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mScroller.computeScrollOffset()) {
            ((View)getParent()).scrollTo(mScroller.getCurrX(),mScroller.getCurrY());
            invalidate();
        }
    }
}