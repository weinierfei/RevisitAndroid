package com.dragon.revisitandroid;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.List;

import javax.net.ssl.HttpsURLConnection;

/**
 * Description:
 *
 * @author guoyongping
 * @date 2019-08-31 10:42
 */
public class Test extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new Thread(new Runnable() {
            @Override
            public void run() {

            }
        }).start();
    }

    public static HttpsURLConnection getHttpURLConnection2() {
        HttpsURLConnection mHttpsURLConnection = null;
        List<NameValuePair> postParams = null;

        postParams.add(new BasicNameValuePair("ip","59.108.54.37"));

        return mHttpsURLConnection;
    }


}
