package com.serhiiv.trip.di.fetures

import com.serhiiv.trip.data.CurrentLocationProvider
import com.serhiiv.trip.di.PerActivity
import com.serhiiv.trip.maps.MapsActivity
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector

@Module
interface FeatureMapsModule {
    @PerActivity
    @ContributesAndroidInjector(modules = [ProvidesModule::class])
    fun contributeMapsActivity(): MapsActivity

    @Module
    class ProvidesModule {
        @Provides
        @PerActivity
        fun provideCurrentLocationProvider(activity: MapsActivity): CurrentLocationProvider {
            return CurrentLocationProvider(activity)
        }
    }
}
