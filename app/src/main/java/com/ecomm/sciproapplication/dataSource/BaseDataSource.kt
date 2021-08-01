package com.ecomm.sciproapplication.dataSource

import android.util.Log
import com.ecom.ecomm.utils.FirebaseResponseType
import com.ecomm.sciproapplication.utils.DataState
import com.ecomm.sciproapplication.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow

/**
 * @author Nelson Kwami
 * cette classe permet de router toutes nos donnees distantes en DataState
 */

private const val TAG = "BaseDataSource"
abstract class BaseDataSource {

    protected fun <T> getFirebaseResult(call: () -> Flow<FirebaseResponseType<T>>): Flow<Resource<T>> =
        flow {
            call().collect {
                when (it) {
                    is FirebaseResponseType.FirebaseSuccessResponse -> {
                        emit(Resource.success(it.body))
                    }
                    is FirebaseResponseType.FirebaseErrorResponse -> {
                        emit(Resource.error(it.throwable?.message?:"", null))
                    }
                    is FirebaseResponseType.FirebaseEmptyResponse -> {
                        emit(Resource.loading(it.body))
                    }
                }
            }
        }

    private fun <T> error(message: String): Resource<T> {
        Log.d(TAG, message)
        return Resource.error("Network call has failed for a following reason: $message", null)
    }


    /**
     * @author Cedrick tsafix
     * l'équivalent de getResult pour les fonction suspends
     */
    protected suspend fun <T> getResultSuspend(call: suspend () -> FirebaseResponseType<T>): DataState<T> {
        return when (val response = call()) {
            is FirebaseResponseType.FirebaseSuccessResponse -> {
                DataState.Success(response.body)
            }
            is FirebaseResponseType.FirebaseErrorResponse -> {
                DataState.Failure(response.throwable)
            }
            is FirebaseResponseType.FirebaseEmptyResponse -> {
                DataState.Success(response.body)

            }
            else -> {
                DataState.Failure()
            }
        }

    }

}