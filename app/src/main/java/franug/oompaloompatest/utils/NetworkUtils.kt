package franug.oompaloompatest.utils

import franug.oompaloompatest.AndroidApplication

object NetworkUtils {
    fun isNetworkAvailable() = AndroidApplication.getInstance?.isConnected() ?: false
}