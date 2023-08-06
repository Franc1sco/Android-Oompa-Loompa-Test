package franug.oompaloompatest

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

open class AndroidApplication : Application() {

    companion object {
        private var instance: AndroidApplication? = null

        /**
         * singleton class instance
         */
        val getInstance: AndroidApplication?
            get() {
                if (instance == null) {
                    synchronized(AndroidApplication::class.java) {
                        if (instance == null) {
                            instance = AndroidApplication()
                        }
                    }
                }
                return instance
            }
    }

    override fun onCreate() {
        super.onCreate()

        instance = this
    }

    fun isConnected(): Boolean {
        val cm = applicationContext
            .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val networkCapabilities = cm.activeNetwork ?: return false
        val actNw =
            cm.getNetworkCapabilities(networkCapabilities) ?: return false
        val result = when {
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
        return result
    }

}