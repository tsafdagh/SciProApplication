package com.ecomm.sciproapplication.utils

import android.content.Context
import android.content.Context.CONNECTIVITY_SERVICE
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.util.Log
import androidx.lifecycle.LiveData

/**
 * Connectivity live data
 *
 * @author Tsafix
 * @constructor
 *
 * @param context
 */
class ConnectivityLiveData(context: Context) : LiveData<Boolean>() {

    private val TAG = "C-Manager"
    private lateinit var networkCallback: ConnectivityManager.NetworkCallback

    private val cm = context.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager

    private val validNetworks: MutableSet<Network> = HashSet()

    private fun checkValidNetworks() {
        postValue(validNetworks.size > 0)
    }

    override fun onActive() {
        networkCallback = createNetworkCallback()
        val networkRequest = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .build()
        cm.registerNetworkCallback(networkRequest, networkCallback)
    }

    override fun onInactive() {
        cm.unregisterNetworkCallback(networkCallback)
    }

    private fun createNetworkCallback() = object : ConnectivityManager.NetworkCallback() {

        /**
         * On available
         * Called when a network is detected. if that network has innternet,
         * save it in the Set object.
         * @param network
         */
        override fun onAvailable(network: Network) {
            Log.d(TAG, "onActive: $network")
            val networkCapabilities = cm.getNetworkCapabilities(network)
            val isInternet =
                networkCapabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            Log.d(TAG, "onAvailable: ${network}, ${isInternet}")
            if (isInternet == true) {
                validNetworks.add(network)
            }
            checkValidNetworks()
        }

        override fun onLost(network: Network) {
            Log.d(TAG, "onLost: ${network}")
            validNetworks.remove(network)
            checkValidNetworks()
        }
    }
}