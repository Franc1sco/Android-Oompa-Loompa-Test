package franug.oompaloompatest.retrofit

import android.content.Context
import franug.oompaloompatest.model.CachingOfflineInterceptor
import franug.oompaloompatest.model.CachingOnlineInterceptor
import franug.oompaloompatest.model.interfaces.ApiService
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "https://2q2woep105.execute-api.eu-west-1.amazonaws.com/"

    fun create(context: Context): ApiService {
        val cacheSize = (5 * 1024 * 1024).toLong() // 5 MB
        val cache = Cache(context.cacheDir, cacheSize)

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(CachingOfflineInterceptor())
            .addNetworkInterceptor(CachingOnlineInterceptor())
            .cache(cache)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(ApiService::class.java)
    }
}