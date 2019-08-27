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
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(TAG, TAG + ".onDestroy")
    }

}
