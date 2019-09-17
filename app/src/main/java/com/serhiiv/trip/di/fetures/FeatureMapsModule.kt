package com.serhiiv.trip.di.fetures

import com.serhiiv.trip.di.PerActivity
import com.serhiiv.trip.maps.MapsActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface FeatureMapsModule {
    @PerActivity
    @ContributesAndroidInjector
    fun contributeMapsActivity(): MapsActivity
}
