package com.masliaiev.points.presentation.screens.points_list

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.masliaiev.points.presentation.core.components.ContentField
import com.masliaiev.points.presentation.core.components.PointsTopAppBar

@Composable
fun PointsListScreen(
    viewModel: PointsListScreenViewModel,
    onNavigateBack: () -> Unit
) {

    val pointsList = viewModel.pointsList
    val lazyPagingItems = pointsList.collectAsLazyPagingItems()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        backgroundColor = Color.White,
        topBar = {
            PointsTopAppBar(
                title = "Saved points",
                navigationIcon = {
                    IconButton(onClick = { onNavigateBack() }) {
                        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "back")
                    }
                },
                elevation = 8.dp
            )
        }
    ) { paddingValues ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            LazyColumn(
                contentPadding = WindowInsets.navigationBars.asPaddingValues()
            ) {
                items(lazyPagingItems) {
                    it?.let {
                        PointCard(
                            modifier = Modifier.padding(12.dp),
                            pointName = it.name,
                            pointLatitude = it.latitude,
                            pointLongitude = it.longitude
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun PointCard(
    modifier: Modifier = Modifier,
    pointName: String,
    pointLatitude: Double,
    pointLongitude: Double,
) {
    Card(
        modifier = modifier,
        elevation = 4.dp,
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            ContentField(
                modifier = Modifier.padding(12.dp),
                description = "Point",
                text = pointName
            )
            ContentField(
                modifier = Modifier.padding(12.dp),
                description = "Latitude",
                text = pointLatitude.toString()
            )
            ContentField(
                modifier = Modifier.padding(12.dp),
                description = "Longitude",
                text = pointLongitude.toString()
            )
        }
    }

}