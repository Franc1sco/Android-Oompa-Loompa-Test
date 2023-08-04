package franug.oompaloompatest.model

import okhttp3.Interceptor
import okhttp3.Response

class CachingInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalResponse = chain.proceed(chain.request())
        return originalResponse.newBuilder()
            .header("Cache-Control", "public, max-age=120")
            .build()
    }
}