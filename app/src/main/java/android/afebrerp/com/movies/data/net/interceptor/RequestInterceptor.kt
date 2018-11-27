package android.afebrerp.com.movies.data.net.interceptor


import android.afebrerp.com.movies.data.entity.ApiKeyContainer
import android.afebrerp.com.movies.data.net.ApiConstants
import com.afebrerp.movies.android.BuildConfig
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Response

class RequestInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain?): Response {
        var request = chain?.request()
        var urlBuilder: HttpUrl.Builder= HttpUrl.Builder()

        request?.url()?.let { it ->
            urlBuilder = it.newBuilder()
        }

        //put common queries
        urlBuilder.addEncodedQueryParameter(
                ApiConstants.QUERY_PARAM_API_KEY,
                ApiKeyContainer.apiKey
        ) //put your own api_key

        urlBuilder.build()?.let { httpUrl ->
            request = request?.newBuilder()?.url(httpUrl)?.build()
        }

        if (BuildConfig.DEBUG) println("URl -> " + request?.url())

        return chain?.proceed(request!!)!!
    }
}