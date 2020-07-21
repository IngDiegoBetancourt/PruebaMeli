package com.diegobetancourt.meli

import android.app.Application
import com.diegobetancourt.meli.di.networkModule
import com.diegobetancourt.meli.di.productsModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class AppMeli : Application(){

    override fun onCreate() {
        super.onCreate()

        // Start Koin application
        startKoin {
            // Koin Android logger
            androidLogger(Level.DEBUG)
            // declare used Android context
            androidContext(this@AppMeli)
            // declare modules
            modules(listOf(networkModule, productsModule))
        }
    }
}