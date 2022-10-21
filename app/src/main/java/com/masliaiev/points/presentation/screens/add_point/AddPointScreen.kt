package com.masliaiev.points.presentation.screens.add_point

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.LatLng
import com.masliaiev.points.domain.entity.Point
import com.masliaiev.points.presentation.core.components.AppEdittext
import com.masliaiev.points.presentation.core.components.PointsTopAppBar
import com.masliaiev.points.presentation.core.components.PrimaryButton
import com.masliaiev.points.presentation.screens.profile.ProfileScreenViewModel

@Composable
fun AddPointScreen(
    viewModel: AddPointScreenViewModel,
    userLogin: String?,
    latitude: Double?,
    longitude: Double?,
    onSaveClick: (name: String) -> Unit,
    onNavigateBack: () -> Unit
) {
    var text by remember { mutableStateOf("") }
    var error by remember { mutableStateOf(false) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        backgroundColor = Color.White,
        topBar = {
            PointsTopAppBar(
                title = "Add point",
                navigationIcon = {
                    IconButton(onClick = { onNavigateBack() }) {
                        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "back")
                    }
                },
                elevation = 8.dp
            )
        }
    ) {paddingValues ->
        Column (modifier = Modifier.padding(paddingValues).navigationBarsPadding()) {
            AppEdittext(
                modifier = Modifier.padding(12.dp),
                hint = "Point name",
                isError = error
            ) {
                text = it
                if (it.isEmpty()) error = true
            }
            PrimaryButton(
                text = "Save",
                isEnabled = true,
                modifier = Modifier.padding(12.dp)
            ) {
                if (text.isNotEmpty()) {
                    viewModel.savePoint(Point(text, latitude ?: 0.0, longitude?: 0.0, userLogin?: ""))
                    onSaveClick(text)
                }
            }
        }
    }


}