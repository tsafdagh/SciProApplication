package com.ecomm.sciproapplication.di

import com.ecomm.sciproapplication.dataSource.storage.StorageDataSourceImpl
import com.ecomm.sciproapplication.dataSource.storage.StorageDataSource
import com.ecomm.sciproapplication.dataSource.storage.StorageService
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import javax.inject.Singleton

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
@InstallIn(SingletonComponent::class)
@Module
object AppModule {


    @Provides
    @Singleton
    fun provideFirestoreDataSource(
        storageService: StorageService
    ): StorageDataSource {
        return StorageDataSourceImpl(storageService)
    }

    /**
     * Provide firestore reference
     *
     * @return StorageReference
     */
    @Singleton
    @Provides
    fun provideFirestoreReference(): StorageReference {
        return FirebaseStorage.getInstance().reference
    }

    /**
     * Provide firestore instance
     *
     * @return FirebaseFirestore
     */
    @Singleton
    @Provides
    fun provideFirestoreInstance(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }


}