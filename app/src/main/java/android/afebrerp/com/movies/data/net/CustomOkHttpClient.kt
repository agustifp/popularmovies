package android.afebrerp.com.movies.data.net

import android.util.Log
import com.afebrerp.movies.android.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit


object CustomOkHttpClient {

    private const val TIME_OUT_MILLII = 10000
    private const val NUM_RETRIES = 3

    fun createBackendOkHttpClient(): OkHttpClient? {

        try {

            return OkHttpClient.Builder()
                .addInterceptor { chain ->
                    val request = chain.request()

                    var response = chain.proceed(request)

                    var tryCount = 0
                    while (!response.isSuccessful && tryCount < NUM_RETRIES) {
                        tryCount++
                        response = chain.proceed(request)
                    }

                    response
                }
                .addInterceptor(getLoginInterceptor())
                .connectTimeout(ApiConstants.TIMEOUT_CONNECTION_VALUE, TimeUnit.SECONDS)
                .readTimeout(ApiConstants.TIMEOUT_READ_VALUE, TimeUnit.SECONDS)
                .writeTimeout(ApiConstants.TIMEOUT_WRITE_VALUE, TimeUnit.SECONDS)
                .hostnameVerifier { _, _ ->
                    // Verify all hostnames
                    true
                }.build()
        } catch (e: Exception) {
            Log.e("Error", "CustomOkHttpClient", e)
            return null
        }

    }

    private fun getLoginInterceptor(): Interceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG) {
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        } else {
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.NONE
        }
        return httpLoggingInterceptor
    }
}
