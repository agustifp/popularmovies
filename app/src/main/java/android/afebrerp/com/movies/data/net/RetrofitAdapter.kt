package android.afebrerp.com.movies.data.net

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import org.koin.standalone.KoinComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitAdapter : KoinComponent {

    fun createMoviesApiRetrofit(): MoviesAPI = Retrofit.Builder()
            .baseUrl(ApiConstants.BASE_URL)
            .client(CustomOkHttpClient.createOkHttpClient()!!)
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(MoviesAPI::class.java)
}