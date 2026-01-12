package com.codewhere.livestore

import android.app.Application
import com.codewhere.livestore.di.networkModule
import com.codewhere.livestore.di.repositoryModule
import com.codewhere.livestore.di.useCaseModule
import com.codewhere.livestore.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class LiveStoreApp: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@LiveStoreApp)
            modules(
                networkModule,
                repositoryModule,
                viewModelModule,
                useCaseModule
            )
        }
    }
}