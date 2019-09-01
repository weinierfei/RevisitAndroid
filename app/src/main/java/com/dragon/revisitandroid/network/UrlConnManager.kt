package com.dragon.revisitandroid.network

import android.text.TextUtils
import org.apache.http.NameValuePair
import java.io.*
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder

/**
 * Description:
 *
 * @author guoyongping
 * @date   2019-09-01 10:12
 */
class UrlConnManager {

    companion object {
        /**
         * post 请求
         */
        public fun getHttpURLConnection(url: String): HttpURLConnection? {
            var mHttpsURLConnection: HttpURLConnection? = null

            val mURL = URL(url)
            mHttpsURLConnection = mURL.openConnection() as HttpURLConnection
            // 设置超时时间
            mHttpsURLConnection?.connectTimeout = 15000
            mHttpsURLConnection?.readTimeout = 15000
            // 设置请求参数
            mHttpsURLConnection?.requestMethod = "POST"
            //天机header
            mHttpsURLConnection?.setRequestProperty("Connection", "Keep-Alive")
            //接收输入流
            mHttpsURLConnection?.doInput = true
            // 传递参数开启
            mHttpsURLConnection?.doOutput = true

            return mHttpsURLConnection
        }


        public fun postParams(output: OutputStream, paramsList: List<NameValuePair>) {
            val mStringBuilder = StringBuilder()
            for (pair in paramsList) {
                if (!TextUtils.isEmpty(mStringBuilder)) {
                    mStringBuilder.append("&")
                }

                mStringBuilder.append(URLEncoder.encode(pair.name, "UTF-8"))
                mStringBuilder.append("=")
                mStringBuilder.append(URLEncoder.encode(pair.value, "UTF-8"))
            }

            val writer = BufferedWriter(OutputStreamWriter(output, "UTF-8"))
            writer.write(mStringBuilder.toString())
            writer.flush()
            writer.close()
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


}