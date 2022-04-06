package com.deny.ibitur.app.ui.activities

import android.app.Application
import com.deny.ibitur.app.di.atividadeModule
import com.deny.ibitur.app.di.carroselModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MyApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin{
            androidLogger()
            androidContext(this@MyApplication)
            modules(atividadeModule, carroselModule)
        }
    }
}