package com.masliaiev.points.presentation.screens.map

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material.FloatingActionButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Place
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import com.masliaiev.points.domain.entity.Point
import com.masliaiev.points.presentation.core.components.CircleButton
import kotlinx.coroutines.flow.MutableSharedFlow

@SuppressLint("MissingPermission")
@Composable
fun AppMapScreen(
    viewModel: AppMapViewModel,
    onNavigateToProfile: (userLogin: String) -> Unit,
    onNavigateToPointsList: () -> Unit,
    onNavigateToAddPoint: (userLogin: String, latitude: Double, longitude: Double) -> Unit,
) {

    val screenState = viewModel.screenState

    val showCurrentPosition = viewModel.showCurrentPosition
    val context = LocalContext.current
    val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

    RequestLocationPermissions { permissions ->
        viewModel.checkPermissions(permissions)
    }

    RequestNotificationsPermissions()

    fusedLocationClient.lastLocation
        .addOnSuccessListener { location ->
            viewModel.setUserCurrentCoordinates(LatLng(location.latitude, location.longitude))
        }

    Scaffold {
        if (screenState.showMapIsPermitted) {
            ShowMap(
                currentCoordinates = screenState.currentCoordinates,
                pointsList = screenState.pointsList,
                onAddPoint = { coordinates ->
                    onNavigateToAddPoint(
                        screenState.userLogin,
                        coordinates.latitude,
                        coordinates.longitude
                    )
                },
                showCurrentPosition = showCurrentPosition
            )
        }

        Column(
            horizontalAlignment = Alignment.End,
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .systemBarsPadding(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                CircleButton(
                    modifier = Modifier.padding(12.dp),
                    icon = {
                        Icon(
                            imageVector = Icons.Filled.Person,
                            contentDescription = "Profile"
                        )
                    },
                    backgroundColor = MaterialTheme.colors.primaryVariant,
                    elevation = FloatingActionButtonDefaults.elevation(),
                    onClick = {
                        if (screenState.userLogin.isNotEmpty()) onNavigateToProfile(
                            screenState.userLogin
                        )
                    }
                )
                CircleButton(
                    modifier = Modifier.padding(12.dp),
                    icon = {
                        Icon(
                            imageVector = Icons.Filled.List,
                            contentDescription = "Points list"
                        )
                    },
                    backgroundColor = MaterialTheme.colors.primaryVariant,
                    elevation = FloatingActionButtonDefaults.elevation(),
                    onClick = { onNavigateToPointsList() }
                )
            }
            CircleButton(
                modifier = Modifier.padding(12.dp),
                icon = {
                    Icon(
                        imageVector = Icons.Filled.Place,
                        contentDescription = "Place"
                    )
                },
                backgroundColor = MaterialTheme.colors.primaryVariant,
                elevation = FloatingActionButtonDefaults.elevation(),
                onClick = {
                    viewModel.showCurrentPosition()
                }
            )
        }
    }
}

@Composable
fun ShowMap(
    pointsList: List<Point>,
    currentCoordinates: LatLng,
    showCurrentPosition: MutableSharedFlow<Unit>,
    onAddPoint: (LatLng) -> Unit
) {

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(currentCoordinates, 15f)
    }

    LaunchedEffect(key1 = true) {
        showCurrentPosition.collect {
            cameraPositionState.move(
                CameraUpdateFactory.newLatLng(
                    LatLng(
                        currentCoordinates.latitude,
                        currentCoordinates.longitude
                    )
                )
            )
        }
    }

    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState,
        properties = MapProperties(isMyLocationEnabled = true),
        uiSettings = MapUiSettings(myLocationButtonEnabled = false, zoomControlsEnabled = false),
        onMapLongClick = {
            onAddPoint(it)
        },
        contentPadding = WindowInsets.navigationBars.asPaddingValues()
    ) {
        for (point in pointsList) {
            Marker(
                state = MarkerState(position = LatLng(point.latitude, point.longitude)),
                title = point.name,
                snippet = "Marker in position"
            )
        }
    }
}

@Composable
fun RequestLocationPermissions(
    onPermissionsUpdated: (permissions: Map<String, Boolean>) -> Unit
) {
    val requestPermission = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        onPermissionsUpdated(permissions)
    }

    SideEffect {
        requestPermission.launch(
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        )
    }
}

@Composable
fun RequestNotificationsPermissions() {
    val requestPermission = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        Log.d("FCM", "API 33+ FCM permission granted -> $isGranted")
    }

    val context = LocalContext.current

    SideEffect {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS
                ) !=
                PackageManager.PERMISSION_GRANTED
            ) {
                requestPermission.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun AppMapScreenPreview() {
//    AppMapScreen()
//}





