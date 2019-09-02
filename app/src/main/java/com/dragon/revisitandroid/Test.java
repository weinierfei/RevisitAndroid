package com.dragon.revisitandroid;

import android.os.Handler;
import android.util.Log;

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

    }


}
