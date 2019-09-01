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
import com.dragon.revisitandroid.network.UrlConnManager
import kotlinx.android.synthetic.main.activity_main.*
import org.apache.http.NameValuePair
import org.apache.http.message.BasicNameValuePair
import java.io.InputStream


class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"
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
            startActivity(Intent(this, Main2Activity::class.java))
        })


        Thread(Runnable {
            useHttpUrlConnectionPost("http://ip.taobao.com/service/getIpInfo.php")
        }).start()
    }


    private fun useHttpUrlConnectionPost(url: String) {
        var mInputStream: InputStream? = null
        val mHttpsURLConnection = UrlConnManager.getHttpURLConnection(url)
        val postParams = ArrayList<NameValuePair>()
        postParams.add(BasicNameValuePair("ip", "59.108.54.37"))
        mHttpsURLConnection?.outputStream?.let { UrlConnManager.postParams(it, postParams) }
        mHttpsURLConnection?.connect()
        mInputStream = mHttpsURLConnection?.inputStream
        val code = mHttpsURLConnection?.responseCode
        val respose = mInputStream?.let { UrlConnManager.converStreamToString(it) }
        Log.i(TAG, "状态码=" + code + "\n 请求结果=" + respose)
        mInputStream?.close()
    }


    override fun onDestroy() {
        super.onDestroy()
        Log.i(TAG, TAG + ".onDestroy")
    }

}
