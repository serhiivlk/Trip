package com.serhiiv.trip.maps

import androidx.lifecycle.*
import com.google.android.gms.maps.model.LatLng
import com.serhiiv.trip.data.CurrentLocationProvider
import com.serhiiv.trip.data.model.DirectionsResponse
import com.serhiiv.trip.data.repository.DirectionsRepository
import com.serhiiv.trip.misc.Event
import com.serhiiv.trip.utils.Logger
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@UseExperimental(ExperimentalCoroutinesApi::class, FlowPreview::class)
class MapsViewModel @Inject constructor(
    private val currentLocationProvider: CurrentLocationProvider,
    private val repository: DirectionsRepository
) : ViewModel() {
    private val originPositionChanel = ConflatedBroadcastChannel<CurrentPositionWrapper>()
    private val destinationPositionChanel = ConflatedBroadcastChannel<LatLng>()

    private val mutableState = MutableLiveData(UiState())
    val state: LiveData<UiState>
        get() = mutableState

    private val originPositionFlow
        get() = originPositionChanel.asFlow()
    private val destinationPositionFlow
        get() = destinationPositionChanel.asFlow()

    init {
        viewModelScope.launch {
            originPositionFlow.collect {
                updateState {
                    copy(
                        currentLocationIsEnable = it.currentIsEnable,
                        originPosition = it.position
                    )
                }
            }
        }
        viewModelScope.launch {
            destinationPositionFlow.collect {
                updateState { copy(destinationPosition = it, polylinePoints = null) }

                kotlin.runCatching {
                    repository.getDestination(
                        state.value?.originPosition!!,
                        it
                    )
                }
                    .onSuccess(::handleDirections)
                    .onFailure(::handleFailure)
            }
        }
    }

    private fun handleDirections(directions: DirectionsResponse) {
        if (directions.status == "OK") {
            val route = directions.routes.first()
            val leg = route.legs.first()
            val distanceInfo = "Distance: ${leg.distance?.text}"
            val durationInfo = "Duration: ${leg.duration?.text}"
            val points = route.overviewPolyline?.points
            updateState {
                copy(
                    tripInfo = distanceInfo,
                    infoMessage = Event("$distanceInfo\n$durationInfo"),
                    polylinePoints = points
                )
            }
        } else {
            Logger.e(directions.errorMessage ?: directions.status ?: "Api Error")
            updateState {
                copy(
                    polylinePoints = null,
                    error = Event(directions.status ?: "Api error")
                )
            }
        }
    }

    private fun handleFailure(error: Throwable) {
        Logger.e(error)
        updateState { copy(error = Event(error.message ?: "Error")) }
    }

    fun setupCurrentLocation() {
        viewModelScope.launch {
            originPositionChanel.send(runCatching {
                currentLocationProvider.getCurrentPosition()
            }.fold(
                onSuccess = {
                    CurrentPositionWrapper(true, currentLocationProvider.getCurrentPosition())
                },
                onFailure = {
                    Logger.e(it)
                    CurrentPositionWrapper(false, DEFAULT_POSITION)
                }
            ))
        }
    }

    fun processDirection(destination: LatLng) {
        viewModelScope.launch {
            destinationPositionChanel.send(destination)
        }
    }

    private fun updateState(action: UiState.() -> UiState) {
        mutableState.value = action(state.value!!)
    }

    data class UiState(
        val currentLocationIsEnable: Boolean = false,
        val originPosition: LatLng? = null,
        val destinationPosition: LatLng? = null,
        val polylinePoints: String? = null,
        val tripInfo: String? = null,
        val infoMessage: Event<String>? = null,
        val error: Event<String>? = null
    )

    data class TripInfo(
        val distance: String,
        val duration: String
    )

    private data class CurrentPositionWrapper(
        val currentIsEnable: Boolean,
        val position: LatLng
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
