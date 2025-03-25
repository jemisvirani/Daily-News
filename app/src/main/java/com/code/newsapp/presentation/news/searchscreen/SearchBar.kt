package com.code.newsapp.presentation.news.searchscreen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import com.code.newsapp.R
import com.code.newsapp.presentation.news.viewmodel.SearchNewsViewModel
import network.chaintech.sdpcomposemultiplatform.sdp
import network.chaintech.sdpcomposemultiplatform.ssp

@Composable
fun SearchBar(
    text: String,
    hint: String,
    modifier: Modifier = Modifier,
    isEnabled: (Boolean) = true,
    height: Dp = 60.sdp,
    elevation: Dp = 3.sdp,
    cornerShape: Shape = RoundedCornerShape(8.sdp),
    backgroundColor: Color = Color.White,
    onSearchClicked: () -> Unit = {},
    onTextChange: (String) -> Unit = {},
    readOnly: Boolean,
    onClick: (() -> Unit)? = null,
    onValueChange: (String) -> Unit,
    onSearch: () -> Unit,
    searchNewsViewModel: SearchNewsViewModel
) {

    val configuration = LocalConfiguration.current
    val isTablet = configuration.screenWidthDp >= 500 // Check if it's a tablet

    val height = if (isTablet) 60.sdp else 70.sdp
    val horizontalPadding = if (isTablet) 11.sdp else 14.sdp
    val verticalPadding = if (isTablet) 15.sdp else 15.sdp
    val searchPadding = if (isTablet) 8.sdp else 10.sdp

    val interactionSource = remember {
        MutableInteractionSource()
    }
    val isCllicked = interactionSource.collectIsPressedAsState().value
    LaunchedEffect(key1 = isCllicked) {
        if (isCllicked) {
            onClick?.invoke()
        }
    }

    val TextFieldValueSaver = listSaver<TextFieldValue, Any>(
        save = { listOf(it.text, it.selection.start, it.selection.end) },
        restore = { TextFieldValue(it[0] as String, TextRange(it[1] as Int, it[2] as Int)) }
    )

    var text = rememberSaveable(stateSaver = TextFieldValueSaver) { mutableStateOf(TextFieldValue()) }

    Row(
        modifier = Modifier
            .height(height)
            .padding(horizontal = horizontalPadding, verticalPadding)
            .fillMaxWidth()
            .shadow(elevation = elevation, shape = cornerShape)
            .background(color = backgroundColor, shape = cornerShape)
            .border(
                width = 1.sdp, colorResource(
                    id = R.color.black
                ), cornerShape
            )
            .clickable {
                if (onClick != null) {
                    onClick()
                }
            },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        val keyboardController = LocalSoftwareKeyboardController.current

        BasicTextField(
            modifier = modifier
                .weight(5f)
                .fillMaxWidth()
                .padding(horizontal = 24.sdp),
            value = text.value,
            onValueChange = { newTextFieldValue->
                val trimmedText = newTextFieldValue.text.trim()  // Remove extra spaces
                text.value = newTextFieldValue.copy(text = trimmedText)
                onValueChange(trimmedText)
                onTextChange(trimmedText)
                Log.e("ChangeTextWord", trimmedText)
            },
            enabled = isEnabled,
            textStyle = TextStyle(
                color = colorResource(id = R.color.black),
                fontSize = 16.ssp,
                fontWeight = FontWeight.Bold
            ),
            decorationBox = { innerTextField ->
                Box(
                    modifier = Modifier.fillMaxSize(), // Takes full available space
                    contentAlignment = Alignment.CenterStart // Centers text vertically and horizontally
                ) {
                    if (text.value.text.isEmpty()) {
                        Text(
                            text = hint,
                            color = Color.Gray.copy(alpha = 0.5f),
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Start // Ensures the hint is centered horizontally
                        )
                    }
                    innerTextField() // Renders the text field
                }
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(onSearch = { onSearch()
                keyboardController?.hide()}),
            singleLine = true
        )
        Box(
            modifier = modifier
                .weight(1f)
                .size(40.sdp)
                .background(color = Color.Transparent, shape = CircleShape)
                .clickable {
                    if (text.value.text.isNotEmpty()) {
                        text.value = TextFieldValue(text = "")
                        onTextChange("")
                        searchNewsViewModel.emptyMutableSearch.value = false
                    }
                }
        ) {
            if (text.value.text.isNotEmpty()) {
                Icon(
                    modifier = modifier
                        .fillMaxSize()
                        .padding(searchPadding),
                    imageVector = Icons.Default.Close,
                    contentDescription = stringResource(R.string.app_name),
                    tint = colorResource(id = R.color.black),
                )
            } else {
                Icon(
                    modifier = modifier
                        .fillMaxSize()
                        .padding(searchPadding),
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search",
                    tint = colorResource(id = R.color.black),
                )
            }
        }
    }
}