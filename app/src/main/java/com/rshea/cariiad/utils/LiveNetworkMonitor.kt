package com.rshea.cariiad.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

interface NetworkMonitor {
    val isConnected: Boolean
}

/**
 * LiveNetworkMonitor checks if the current network signal is live or not and set ture in isConnected if it's alive
 *
 * @property applicationContext Context
 * @property isConnected Boolean - Use this public value to check if the network is alive or not
 * @constructor
 */

class LiveNetworkMonitor(context: Context) : NetworkMonitor {
    private val applicationContext: Context

    init {
        applicationContext = context.applicationContext
    }

    override val isConnected: Boolean
        get() {
            val connectivityManager =
                applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val network = connectivityManager.activeNetwork ?: return false
            val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false
            return when {
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                else -> false
            }
        }
}