package com.masliaiev.points.presentation.core.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.masliaiev.points.presentation.core.theme.PointsTheme

@Composable
fun AppEdittext(
    modifier: Modifier = Modifier,
    initialText: String = "",
    hint: String = "",
    isPasswordField: Boolean = false,
    isError: Boolean = false,
    onValueChanged: (String) -> Unit = {}
) {
    var text by remember { mutableStateOf(initialText) }

    Column(modifier = modifier) {
        OutlinedTextField(
            value = text,
            onValueChange = {
                text = it
                onValueChanged(text)
            },
            modifier = Modifier
                .fillMaxWidth(),
            label = { Text(text = hint) },
            shape = RoundedCornerShape(8.dp),
            isError = isError,
            trailingIcon = {
                if (isError) Icon(
                    imageVector = Icons.Filled.Warning,
                    contentDescription = "error",
                    tint = MaterialTheme.colors.error
                )
            },
            singleLine = true,
            visualTransformation = if (isPasswordField) PasswordVisualTransformation() else VisualTransformation.None
        )
        if (isError) {
            Text(
                modifier = Modifier.padding(start = 8.dp),
                text = "Field may not be empty",
                color = MaterialTheme.colors.error,
                style = MaterialTheme.typography.caption
            )
        }
    }
}

@Composable
fun ContentField(
    modifier: Modifier = Modifier,
    description: String,
    text: String,
    descriptionFontSize: TextUnit = TextUnit.Unspecified,
    textFontSize: TextUnit = TextUnit.Unspecified
) {
    Row(modifier = modifier) {
        Text(
            text = "$description:",
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            fontSize = descriptionFontSize
        )
        Text(
            text = text,
            fontSize = textFontSize
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TextFieldsPreview() {
    PointsTheme {
        Column {
            AppEdittext(modifier = Modifier.padding(12.dp))
            AppEdittext(modifier = Modifier.padding(12.dp), isError = true)
            ContentField(
                modifier = Modifier.padding(12.dp),
                description = "Description",
                text = "Text",
                descriptionFontSize = 18.sp,
                textFontSize = 18.sp
            )
        }
    }
}