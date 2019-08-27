package com.dragon.revisitandroid

import android.app.Service
import android.content.Intent
import android.content.ServiceConnection
import android.os.Binder
import android.os.IBinder
import android.util.Log

/**
 * Description:
 * @author: guoyongping
 * @date:  2019-08-27 16:45
 */
class MyService : Service() {
    private val TAG = "MyService"
    private val binder: MyBinder = MyBinder()


    override fun onCreate() {
        super.onCreate()
        Log.i(TAG, "MyService.onCreate")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.i(TAG, "MyService.onStartCommand")
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(intent: Intent?): IBinder? {
        Log.i(TAG, "MyService.onBind")
        return binder
    }

    override fun onUnbind(intent: Intent?): Boolean {
        Log.i(TAG, "MyService.onUnbind")
        return super.onUnbind(intent)
    }

    override fun bindService(service: Intent?, conn: ServiceConnection, flags: Int): Boolean {
        Log.i(TAG, "MyService.bindService")
        return super.bindService(service, conn, flags)
    }

    override fun unbindService(conn: ServiceConnection) {
        super.unbindService(conn)
        Log.i(TAG, "MyService.unbindService")
    }


    override fun stopService(name: Intent?): Boolean {
        Log.i(TAG, "MyService.stopService")
        return super.stopService(name)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(TAG, "MyService.onDestroy")
    }

    companion object
    class MyBinder : Binder() {

    }

}