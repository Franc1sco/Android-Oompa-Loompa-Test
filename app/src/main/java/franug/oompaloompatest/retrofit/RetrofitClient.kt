package franug.oompaloompatest.retrofit

import franug.oompaloompatest.model.CachingInterceptor
import franug.oompaloompatest.model.interfaces.ApiService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "https://2q2woep105.execute-api.eu-west-1.amazonaws.com/"

    fun create(): ApiService {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(CachingInterceptor())
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(ApiService::class.java)
    }
}