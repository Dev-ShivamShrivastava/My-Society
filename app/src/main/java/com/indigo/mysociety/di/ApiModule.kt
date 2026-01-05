package com.indigo.mysociety.di

import android.content.Context
import com.data.api.ApiInterface
import java.util.concurrent.TimeUnit
import com.indigo.mysociety.dataStores.DataStorePrefs
import com.indigo.mysociety.dataStores.PreferenceKeys
import com.indigo.mysociety.utils.HEADER_AUTHORIZATION_KEY
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okio.Timeout
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApiModule {

    @Singleton
    @Provides
    fun provideDataStore(@ApplicationContext context: Context) = DataStorePrefs(context)

//    @Provides
//    fun provideBaseUrl() = "https://expositorial-deistically-clementina.ngrok-free.dev/"//"http://192.168.0.110:4000/"

    @Provides
    fun provideBaseUrl() = "http://192.168.0.111:3000/"

    @Provides
    fun provideHeaderInterceptor(
        dataStore: DataStorePrefs
    ): Interceptor{
        return Interceptor{ chain ->
            val token = runBlocking {
                dataStore.readToken(PreferenceKeys.KEY_AUTH_TOKEN).first()
            }
            println("token $token")
            val request = chain.request().newBuilder().apply {
                if (token.isNotEmpty()){
                    addHeader(HEADER_AUTHORIZATION_KEY,token)
                }
            }.build()

            chain.proceed(request)
        }
    }

    @Singleton
    @Provides
    fun provideMoshi(): Moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

    @Singleton
    @Provides
    fun provideHttpClient(headerInterceptor: Interceptor): OkHttpClient {
        val loggingInterCeptor = HttpLoggingInterceptor()
        loggingInterCeptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient.Builder().addNetworkInterceptor(headerInterceptor)
            .addInterceptor(loggingInterCeptor)
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(moshi: Moshi, okHttpClient: OkHttpClient,baseUrl: String): Retrofit {
        return Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl(baseUrl).addConverterFactory(
            MoshiConverterFactory.create(moshi)).client(okHttpClient).build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit) = retrofit.create(ApiInterface::class.java)

}