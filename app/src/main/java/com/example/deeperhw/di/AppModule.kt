package com.example.deeperhw.di

import android.content.Context
import com.example.deeperhw.data.DataRepositoryImpl
import com.example.deeperhw.data.local.DataCache
import com.example.deeperhw.data.local.SharedPrefs
import com.example.deeperhw.data.remote.DataApi
import com.example.deeperhw.domain.repository.DataRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDataApi(): DataApi = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl("https://maps.fishdeeper.com/api/")
        .build()
        .create(DataApi::class.java)

    @Provides
    @Singleton
    fun provideDataRepository(api: DataApi, cache: DataCache, sharedPrefs: SharedPrefs): DataRepository =
        DataRepositoryImpl(api, cache, sharedPrefs)

    @Provides
    @Singleton
    fun provideDataCache(): DataCache =
        DataCache()

    @Provides
    @Singleton
    fun provideSharedPrefs(@ApplicationContext appContext: Context): SharedPrefs =
        SharedPrefs(appContext)
}
