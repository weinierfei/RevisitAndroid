package com.dragon.revisitandroid.network.okhttp

import okhttp3.Request
import okhttp3.Response
import java.io.IOException

/**
 * Description: 请求回调
 * @author: guoyongping
 * @date:  2019-09-02 10:56
 */
abstract class ResultCallBack {

    public abstract fun onError(request: Request, e: Exception)

    @Throws(IOException::class)
    public abstract fun onResponse(response: Response)
}