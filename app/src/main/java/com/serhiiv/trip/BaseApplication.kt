package com.serhiiv.trip

import android.content.Context
import com.serhiiv.trip.di.AppComponent
import com.serhiiv.trip.utils.Logger
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import timber.log.Timber

class BaseApplication : DaggerApplication(), App {
    override val context: Context
        get() = applicationContext

    override fun onCreate() {
        super.onCreate()

        initLogger()
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return AppComponent.Initializer.init(this)
    }

    private fun initLogger() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
            Logger.addPrinter(LoggerPrinter.Timber())
        }
    }
}
