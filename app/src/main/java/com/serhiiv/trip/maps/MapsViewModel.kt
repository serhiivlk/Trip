package com.serhiiv.trip.maps

import androidx.lifecycle.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.serhiiv.trip.misc.CurrentLocationProvider
import com.serhiiv.trip.utils.Logger
import kotlinx.coroutines.launch
import javax.inject.Inject

class MapsViewModel @Inject constructor(
    private val currentLocationProvider: CurrentLocationProvider
) : ViewModel() {
    private val mutableState = MutableLiveData<UiState>()
    val state: LiveData<UiState>
        get() = mutableState

    fun setupCurrentLocation() {
        viewModelScope.launch {
            var currentState = state.value ?: UiState()
            currentState = try {
                currentState.copy(
                    currentLocationIsEnable = true,
                    originPosition = currentLocationProvider.getCurrentPosition()
                )
            } catch (e: Exception) {
                Logger.e(e)
                currentState.copy(
                    currentLocationIsEnable = false,
                    originPosition = DEFAULT_POSITION
                )
            }
            mutableState.value = currentState
        }
    }

    data class UiState(
        val currentLocationIsEnable: Boolean = false,
        val originPosition: LatLng? = null,
        val directionOptions: MarkerOptions? = null
    )

    companion object {
        @JvmStatic
        private val DEFAULT_POSITION by lazy {
            LatLng(50.4501, 30.5234) // Kyiv coordinates
        }
    }

    @Suppress("UNCHECKED_CAST")
    class Factory @Inject constructor(
        private val viewModel: MapsViewModel
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return viewModel as T
        }
    }
}
