package android.afebrerp.com.movies.data.net

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import org.koin.standalone.KoinComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class RetrofitAdapter() : KoinComponent {

    fun createBackEndAdapter(): MoviesAPI = Retrofit.Builder()
            .baseUrl(ApiConstants.BASE_URL)
            .client(CustomOkHttpClient.createBackendOkHttpClient()!!)
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(MoviesAPI::class.java)
}