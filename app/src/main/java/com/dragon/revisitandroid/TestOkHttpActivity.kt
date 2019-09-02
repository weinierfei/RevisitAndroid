package com.dragon.revisitandroid

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.dragon.revisitandroid.network.okhttp.OkHttpEngine
import com.dragon.revisitandroid.network.okhttp.ResultCallBack
import okhttp3.*
import java.io.IOException
import java.util.concurrent.TimeUnit

/**
 * Description:
 * @author: guoyongping
 * @date:  2019-09-02 09:54
 */
class TestOkHttpActivity : AppCompatActivity() {
    private val TAG = "TestOkHttpActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // get
//        testOkHttpGet("http://blog.csdn.net/itachi85")
        // post
//        testOkHttpPost("http://ip.taobao.com/service/getIpInfo.php")


        Thread(Runnable {
            OkHttpEngine
                .getInstance(this)
                ?.getAsynHttp("https://www.baidu.com", object : ResultCallBack() {
                    override fun onError(request: Request, e: Exception) {

                    }

                    override fun onResponse(response: Response) {
                        val str = response.body?.string()
                        Log.i(TAG, "--->$str")
                    }
                })
        }).start()
    }

    /**
     * okhttp get请求
     * 1 、创建OKHttpClient、Request、Call
     * 2 、调用call的enqueue方法(异步)
     * 3 、同步调用call的execute方法
     */
    private fun testOkHttpGet(url: String) {
        val requestBuilder = Request.Builder()
        requestBuilder.url(url)
        requestBuilder.method("GET", null)
        val request = requestBuilder.build()
        val mBuild = OkHttpClient.Builder()
        mBuild.connectTimeout(15000, TimeUnit.MILLISECONDS)
        mBuild.writeTimeout(15000, TimeUnit.MILLISECONDS)
        mBuild.readTimeout(15000, TimeUnit.MILLISECONDS)
        val mOkHttpClient = mBuild.build()
        val mCall = mOkHttpClient.newCall(request)
        mCall.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {

            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {
                val str = response.body?.string()
                Log.i(TAG, "--->$str")

            }
        })
        // 取消请求
//        mCall.cancel()
    }

    @Throws(IOException::class)
    private fun testOkHttpPost(url: String) {
        val formBody: RequestBody = FormBody.Builder()
            .add("ip", "219.143.183.2")
            .build()

        val request = Request.Builder()
            .url(url)
            .post(formBody)
            .build()

        val mOkHttpClient = OkHttpClient()
        val mCall = mOkHttpClient.newCall(request)
        mCall.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {

            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {
                val str = response.body?.string()
                Log.i(TAG, "--->$str")
            }
        })
    }
}