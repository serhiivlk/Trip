package com.serhiiv.trip.di

import com.serhiiv.trip.BaseApplication
import com.serhiiv.trip.di.fetures.FeatureMapsModule
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule

@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        FeatureMapsModule::class
    ]
)
@PerApplication
interface AppComponent : AndroidInjector<BaseApplication> {

    @Component.Factory
    interface Factory {
        fun create(): AppComponent
    }

    object Initializer {
        fun init(): AppComponent {
            return DaggerAppComponent.factory()
                .create()
        }
    }
}
