package franug.oompaloompatest.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import androidx.localbroadcastmanager.content.LocalBroadcastManager

class ConnectivityReceiver : BroadcastReceiver() {

    private val NETWORK_AVAILABLE_ACTION = "Connectivity Receiver"
    private val IS_NETWORK_AVAILABLE = "isNetworkAvailable"
    override fun onReceive(context: Context, arg1: Intent) {
        val isConnected = isNetworkConnected(context)

        val networkStateIntent = Intent(NETWORK_AVAILABLE_ACTION)
        networkStateIntent.putExtra(IS_NETWORK_AVAILABLE, isConnected)
        LocalBroadcastManager.getInstance(context).sendBroadcast(networkStateIntent)
    }

    fun isNetworkConnected(context: Context): Boolean {
        val cm = context
            .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val activeNetwork = cm.activeNetworkInfo
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting
    }

    companion object {
        val NETWORK_AVAILABLE_ACTION = "Connectivity Receiver"
        val IS_NETWORK_AVAILABLE = "isNetworkAvailable"
    }
}