package franug.oompaloompatest.model

import franug.oompaloompatest.utils.NetworkUtils
import okhttp3.Interceptor
import okhttp3.Response

class CachingOfflineInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        if (!NetworkUtils.isNetworkAvailable()) {
            val maxStale = 60 * 60 * 24 * 30 // Offline cache available for 30 days
            request = request.newBuilder()
                .header("Cache-Control", "public, only-if-cached, max-stale=$maxStale")
                .removeHeader("Pragma")
                .build()
        }
        return chain.proceed(request)
    }
}