package com.codewhere.livestore.di

import com.codewhere.livestore.common.constants.NetworkChecker
import com.codewhere.livestore.data.network.FakeStoreApi
import io.reactivex.rxjava3.schedulers.Schedulers.single
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
    single {
        OkHttpClient.Builder()
            .build()
    }

    single{
        Retrofit.Builder()
            .baseUrl("https://fakestoreapi.com/")
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()
    }

    single{
        get<Retrofit>().create(FakeStoreApi::class.java)
    }

    single {
        NetworkChecker(androidContext())
    }
}