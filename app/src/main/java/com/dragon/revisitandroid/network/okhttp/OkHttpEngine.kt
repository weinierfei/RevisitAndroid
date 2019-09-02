package com.dragon.revisitandroid.network.okhttp

import android.content.Context
import android.os.Handler
import android.os.Looper
import okhttp3.*
import java.io.IOException
import java.util.concurrent.TimeUnit

/**
 * Description:
 * @author: guoyongping
 * @date:  2019-09-02 11:00
 */
class OkHttpEngine {


    private val mOkHttpClient: OkHttpClient
    private val mHandler: Handler

    constructor(context: Context) {
        val sdCache = context.externalCacheDir
        val cacheSize: Long = 10 * 1024 * 1024
        val builder = OkHttpClient.Builder()
            .connectTimeout(15, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .cache(Cache(sdCache.absoluteFile, cacheSize))
        mOkHttpClient = builder.build()
        mHandler = Handler(Looper.getMainLooper())
    }

    companion object {
        private var mInstance: OkHttpEngine? = null
        fun getInstance(context: Context): OkHttpEngine? {
            if (mInstance == null) {
                synchronized(OkHttpEngine::class.java) {
                    if (mInstance == null) {
                        mInstance = OkHttpEngine(context)
                    }
                }
            }
            return mInstance
        }
    }


    public fun getAsynHttp(url: String, resultCallBack: ResultCallBack) {
        val request = Request.Builder()
            .url(url)
            .build()

        val call = mOkHttpClient.newCall(request)
        dealRequest(call, resultCallBack)
    }

    private fun dealRequest(call: Call, resultCallBack: ResultCallBack) {
        call.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                sendFailedCallback(call.request(), e, resultCallBack)
            }

            override fun onResponse(call: Call, response: Response) {
                sendSuccessCallback(response, resultCallBack)
            }
        })
    }


    private fun sendSuccessCallback(response: Response, callBack: ResultCallBack) {
        mHandler.post {
            try {
                callBack.onResponse(response)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    private fun sendFailedCallback(request: Request, e: Exception, callBack: ResultCallBack) {
        mHandler.post {
            callBack.onError(request, e)
        }
    }
}