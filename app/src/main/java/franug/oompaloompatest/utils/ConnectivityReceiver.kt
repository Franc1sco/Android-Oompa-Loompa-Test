package franug.oompaloompatest.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.localbroadcastmanager.content.LocalBroadcastManager

class ConnectivityReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, arg1: Intent) {
        val isConnected = isNetworkConnected(context)

        val networkStateIntent = Intent(NETWORK_AVAILABLE_ACTION)
        networkStateIntent.putExtra(IS_NETWORK_AVAILABLE, isConnected)
        LocalBroadcastManager.getInstance(context).sendBroadcast(networkStateIntent)
    }

    private fun isNetworkConnected(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkCapabilities = connectivityManager.activeNetwork ?: return false
        val actNw =
            connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
        val result = when {
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }

        return result
    }

    companion object {
        const val NETWORK_AVAILABLE_ACTION = "Connectivity Receiver"
        const val IS_NETWORK_AVAILABLE = "isNetworkAvailable"
    }
}