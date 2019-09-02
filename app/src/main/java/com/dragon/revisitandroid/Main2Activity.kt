package com.dragon.revisitandroid

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.apache.http.HttpVersion
import org.apache.http.client.HttpClient
import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.DefaultHttpClient
import org.apache.http.params.BasicHttpParams
import org.apache.http.params.HttpConnectionParams
import org.apache.http.params.HttpProtocolParams
import org.apache.http.protocol.HTTP
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader


class Main2Activity : AppCompatActivity() {
    private val TAG = "Main2Activity";
    private val connection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            Log.i(TAG, TAG + ".onServiceConnected")
        }

        override fun onServiceDisconnected(name: ComponentName) {
            Log.i(TAG, TAG + ".onServiceDisconnected")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.i(TAG, TAG + ".onCreate")
        btn_start_service.setOnClickListener(View.OnClickListener {
            Log.i(TAG, TAG + ".click->startService")
            startService(Intent(this, MyService::class.java))
        })

        btn_stop_service.setOnClickListener(View.OnClickListener {
            Log.i(TAG, TAG + ".click->stopService")
            stopService(Intent(this, MyService::class.java))
        })

        btn_bind_service.setOnClickListener(View.OnClickListener {
            Log.i(TAG, TAG + ".click->bindService")
            bindService(Intent(this, MyService::class.java), connection, Context.BIND_AUTO_CREATE)
        })

        btn_unbind_service.setOnClickListener(View.OnClickListener {
            Log.i(TAG, TAG + ".click->unbindService")
            unbindService(connection)
        })

        btn_next_activity.setOnClickListener(View.OnClickListener {
            Log.i(TAG, TAG + ".click->next_activity")
            startActivity(Intent(this, Main3Activity::class.java))
        })


        Thread(Runnable {
            useHttpClientGet("https://www.baidu.com")
        }).start()
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(TAG, TAG + ".onDestroy")
    }


    private fun createHttpClient(): HttpClient {
        val mDefaultHttpParams = BasicHttpParams()
        // 设置连接超时
        HttpConnectionParams.setConnectionTimeout(mDefaultHttpParams, 15000)
        // 设置请求超时
        HttpConnectionParams.setSoTimeout(mDefaultHttpParams, 15000)
        HttpConnectionParams.setTcpNoDelay(mDefaultHttpParams, true)
        HttpProtocolParams.setVersion(mDefaultHttpParams, HttpVersion.HTTP_1_1)
        HttpProtocolParams.setContentCharset(mDefaultHttpParams, HTTP.UTF_8)
        // 持续握手
        HttpProtocolParams.setUseExpectContinue(mDefaultHttpParams, true)

        val mHttpClient = DefaultHttpClient(mDefaultHttpParams)

        return mHttpClient
    }


    private fun useHttpClientGet(url: String) {
        val mHttpGet = HttpGet(url)
        mHttpGet.addHeader("Connection", "Keep-Alive")
        try {
            val mHttpClient = createHttpClient()
            val mHttpResponse = mHttpClient.execute(mHttpGet)
            val mHttpEntity = mHttpResponse.entity
            var code = mHttpResponse.statusLine.statusCode
            if (null != mHttpEntity) {
                var mInputStream = mHttpEntity.content
                var respose = converStreamToString(mInputStream)

                Log.i(TAG, "状态码=" + code + "\n 请求结果=" + respose)
                mInputStream.close()

            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    @Throws(IOException::class)
    public fun converStreamToString(mInputStream: InputStream): String {
        val reader = BufferedReader(InputStreamReader(mInputStream))
        val sb = StringBuffer()
        var line: String? = null
        while ((line == reader.readLine()) != null) {
            sb.append(line + "\n")
        }
        return sb.toString()
    }

}
