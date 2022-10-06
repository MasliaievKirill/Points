package com.masliaiev.points.presentation.screens.map

import android.Manifest
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.FloatingActionButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Place
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import com.masliaiev.points.domain.entity.Point
import com.masliaiev.points.presentation.core.components.CircleButton

@Composable
fun AppMapScreen(
    viewModel: AppMapViewModel,
    onNavigateToProfile: (userLogin: String) -> Unit,
    onNavigateToPointsList: () -> Unit,
    onNavigateToAddPoint: (userLogin: String, latitude: Double, longitude: Double) -> Unit,
) {

    val canShowMap = viewModel.showMap
    val currentCoordinates = viewModel.currentCoordinates
    val userLogin = viewModel.userLogin
    val pointsList = viewModel.pointsList.collectAsState(initial = listOf())

    RequestLocationPermissions { permissions ->
        viewModel.checkPermissions(permissions)
    }

    Log.d("LOG_MAP", "RECOMPOSED")

    Scaffold {
        if (canShowMap) {
            Log.d("LOG_COMPOSE", "ES")
            ShowMap(
                currentCoordinates = currentCoordinates,
                pointsList = pointsList.value,
                onAddPoint = {
                    onNavigateToAddPoint(userLogin, it.latitude, it.longitude)
                },
                onLocationUpdate = { viewModel.setUserCurrentCoordinates(it) },
            )
        }

        Column(
            horizontalAlignment = Alignment.End,
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
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
                    onClick = { if (userLogin.isNotEmpty()) onNavigateToProfile(userLogin) }
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
                onClick = { }
            )

        }


    }
}

@Composable
fun ShowMap(
    pointsList: List<Point>,
    currentCoordinates: LatLng,
    onLocationUpdate: (LatLng) -> Unit,
    onAddPoint: (LatLng) -> Unit
) {

    val context = LocalContext.current
    val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(currentCoordinates, 15f)
    }
    fusedLocationClient.lastLocation
        .addOnSuccessListener { location ->
            onLocationUpdate(LatLng(location.latitude, location.longitude))
            cameraPositionState.move(
                CameraUpdateFactory.newLatLng(
                    LatLng(
                        location.latitude,
                        location.longitude
                    )
                )
            )
        }
    GoogleMap(
        modifier = Modifier
            .fillMaxSize(),
        cameraPositionState = cameraPositionState,
        properties = MapProperties(isMyLocationEnabled = true),
        uiSettings = MapUiSettings(myLocationButtonEnabled = false, zoomControlsEnabled = false),
        onMapLongClick = {
            onAddPoint(it)
        }
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

//@Preview(showBackground = true)
//@Composable
//fun AppMapScreenPreview() {
//    AppMapScreen()
//}





