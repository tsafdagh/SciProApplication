package com.ecom.ecomm.utils

/**
 * Firebase response type
 *
 * @param T
 * @constructor Create empty Firebase response type
 * @author tsafack Dagha Cedrick
 */
sealed class FirebaseResponseType<out T> {
    /**
     * Firebase success response
     *
     * @param T
     * @property body
     * @constructor Create empty Firebase success response
     */
    data class FirebaseSuccessResponse<T>(val body: T? = null) : FirebaseResponseType<T>()

    /**
     * Firebase error response
     *
     * @property throwable
     * @constructor Create empty Firebase error response
     */
    data class FirebaseErrorResponse(val throwable: Throwable? = null) :
        FirebaseResponseType<Nothing>()

    /**
     * Firebase empty response
     *
     * @param T
     * @property body
     * @constructor Create empty Firebase empty response
     */
    data class FirebaseEmptyResponse<T>(val body: T?) : FirebaseResponseType<T>()
}