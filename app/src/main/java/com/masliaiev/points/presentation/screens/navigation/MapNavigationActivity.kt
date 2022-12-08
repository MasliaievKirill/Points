package com.masliaiev.points.presentation.screens.navigation

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Looper
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.gms.location.*
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import com.masliaiev.points.R
import com.masliaiev.points.presentation.core.theme.PointsTheme
import com.masliaiev.points.presentation.screens.map.RequestLocationPermissions
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.MutableSharedFlow

@AndroidEntryPoint
class MapNavigationActivity : ComponentActivity() {

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallback: LocationCallback
    private var currentLocation = mutableStateOf(LatLng(0.0, 0.0))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        setupScreenWithLastLocation { coordinates ->
            setContent {
                PointsTheme {
                    MapNavigationScreen(
                        currentCoordinates = currentLocation.value,
                        startCoordinates = coordinates,
                        endCoordinates = LatLng(
                            intent.extras?.getDouble(KEY_LATITUDE, 0.0) ?: 0.0,
                            intent.extras?.getDouble(KEY_LONGITUDE, 0.0) ?: 0.0
                        )
                    )
                }
            }
        }

        locationRequest = LocationRequest.create().apply {
            interval = 10000
            fastestInterval = 5000
            priority = Priority.PRIORITY_HIGH_ACCURACY
        }

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                for (location in locationResult.locations) {
                    currentLocation.value = LatLng(location.latitude, location.longitude)
                }
            }
        }


    }

    override fun onResume() {
        super.onResume()
        startLocationUpdates()
    }

    override fun onPause() {
        super.onPause()
        stopLocationUpdates()
    }

    @SuppressLint("MissingPermission")
    private fun startLocationUpdates() {
        fusedLocationProviderClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.getMainLooper()
        )
    }

    private fun stopLocationUpdates() {
        fusedLocationProviderClient.removeLocationUpdates(locationCallback)
    }

    @SuppressLint("MissingPermission")
    private inline fun setupScreenWithLastLocation(crossinline block: (coordinates: LatLng) -> Unit) {
        fusedLocationProviderClient.lastLocation.addOnSuccessListener { location ->
            block.invoke(LatLng(location.latitude, location.longitude))
        }
    }

    companion object {
        fun newIntent(context: Context, longitude: Double, latitude: Double): Intent {
            return Intent(context, MapNavigationActivity::class.java).apply {
                putExtra(KEY_LONGITUDE, longitude)
                putExtra(KEY_LATITUDE, latitude)
            }
        }

        private const val KEY_LONGITUDE = "longitude"
        private const val KEY_LATITUDE = "latitude"
    }
}

@Composable
fun MapNavigationScreen(
    viewModel: MapNavigationViewModel = hiltViewModel(),
    currentCoordinates: LatLng,
    startCoordinates: LatLng,
    endCoordinates: LatLng,
) {
    val screenState = viewModel.screenState

    RequestLocationPermissions { permissions ->
        viewModel.checkPermissions(permissions)
    }

    LaunchedEffect(key1 = true) {
        viewModel.getRoute(startCoordinates, endCoordinates)
    }

    viewModel.setUserCurrentCoordinates(currentCoordinates)

    if (screenState.showMapIsPermitted) {
        ShowMapNavigation(
            currentCoordinates = screenState.currentCoordinates,
            points = screenState.route,
            showToast = viewModel.showToast
        )
    }
}

@Composable
fun ShowMapNavigation(
    currentCoordinates: LatLng,
    points: List<LatLng>,
    showToast: MutableSharedFlow<String>
) {

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(currentCoordinates, 15f)
    }

    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        showToast.collect {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }
    }

    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState,
        properties = MapProperties(isMyLocationEnabled = true),
        uiSettings = MapUiSettings(myLocationButtonEnabled = false, zoomControlsEnabled = false),
        contentPadding = WindowInsets.navigationBars.asPaddingValues()
    ) {
        if (points.isNotEmpty()) {
            Polyline(points = points)
            Marker(
                state = MarkerState(
                    position = LatLng(
                        points.first().latitude,
                        points.first().longitude
                    )
                ),
                title = "Start",
                snippet = ""
            )
            Marker(
                state = MarkerState(
                    position = LatLng(
                        points.last().latitude,
                        points.last().longitude
                    )
                ),
                title = "End",
                snippet = ""
            )
        }
        Marker(
            state = MarkerState(
                position = LatLng(
                    currentCoordinates.latitude,
                    currentCoordinates.longitude
                )
            ),
            title = "Me",
            snippet = "",
            icon = BitmapDescriptorFactory.fromResource(R.drawable.carline)
        )
    }
}