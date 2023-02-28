package com.rshea.cariiad.di

import com.rshea.cariiad.utils.NetworkMonitor
import okhttp3.Interceptor
import okhttp3.Response
import org.chromium.net.NetworkException

/**
 * RequestInterceptor is a class to implement intercept function from Interceptor interface.
 * If the network is alive, intercept function proceeds the chain request
 *
 * @property networkMonitor NetworkMonitor
 * @constructor
 */
class RequestInterceptor(private val networkMonitor: NetworkMonitor) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        println("Outgoing request to ${request.url}")
        return if (networkMonitor.isConnected) chain.proceed(request) else {
            throw CustomNetworkException("Network Problem", Throwable())
        }
    }

    class CustomNetworkException(message: String?, cause: Throwable?) : NetworkException(message,
        cause
    ) {
        override fun getErrorCode(): Int {
            TODO("Not yet implemented")
        }

        override fun getCronetInternalErrorCode(): Int {
            TODO("Not yet implemented")
        }

        override fun immediatelyRetryable(): Boolean {
            TODO("Not yet implemented")
        }
    }
}