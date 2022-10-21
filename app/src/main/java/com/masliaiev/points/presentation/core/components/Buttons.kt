package com.masliaiev.points.presentation.core.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.List
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.masliaiev.points.presentation.core.theme.PointsTheme

@Composable
fun PrimaryButton(
    modifier: Modifier = Modifier,
    text: String,
    isEnabled: Boolean,
    onClick: () -> Unit
) {
    Box(modifier = modifier) {
        Button(
            shape = RoundedCornerShape(14.dp),
            onClick = onClick,
            enabled = isEnabled,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.button
            )
        }
    }
}

@Composable
fun SecondaryButton(
    modifier: Modifier = Modifier,
    text: String,
    isEnabled: Boolean,
    onClick: () -> Unit
) {
    Box(modifier = modifier) {
        OutlinedButton(
            border = BorderStroke(2.dp, Color.Blue),
            shape = RoundedCornerShape(14.dp),
            onClick = onClick,
            enabled = isEnabled,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.button
            )
        }
    }
}

@Composable
fun CircleButton(
    modifier: Modifier = Modifier,
    icon: @Composable () -> Unit,
    backgroundColor: Color = MaterialTheme.colors.secondary,
    elevation: FloatingActionButtonElevation = FloatingActionButtonDefaults.elevation(),
    onClick: () -> Unit
) {
    Box(modifier = modifier) {
        FloatingActionButton(
            onClick = { onClick() },
            backgroundColor = backgroundColor,
            elevation = elevation
        ) {
            icon()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    PointsTheme {
        Column {
            PrimaryButton(
                text = "Main button",
                isEnabled = true,
                modifier = Modifier.padding(12.dp)
            ) {}
            SecondaryButton(
                text = "Secondary button",
                isEnabled = true,
                modifier = Modifier.padding(12.dp)
            ) {}
            CircleButton(
                icon = {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = "Profile"
                    )
                },
                modifier = Modifier.padding(12.dp)
            ) {}
        }
    }
}