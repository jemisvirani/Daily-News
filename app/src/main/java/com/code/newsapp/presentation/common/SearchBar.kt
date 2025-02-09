package com.code.newsapp.presentation.common

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.isSystemInDarkTheme
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.code.newsapp.R
import com.code.newsapp.ui.theme.NewsAppTheme
import network.chaintech.sdpcomposemultiplatform.sdp
import network.chaintech.sdpcomposemultiplatform.ssp

//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun SearchBar(
//    modifier: Modifier = Modifier,
//    text: String,
//    readOnly: Boolean,
//    onClick: (() -> Unit)? = null,
//    onValueChange: (String) -> Unit,
//    onSearch: () -> Unit
//) {
//
//    val interactionSource = remember {
//        MutableInteractionSource()
//    }
//    val isCllicked = interactionSource.collectIsPressedAsState().value
//    LaunchedEffect(key1 = isCllicked) {
//        if (isCllicked) {
//            onClick?.invoke()
//        }
//    }
//
//    Box(modifier = modifier) {
//        TextField(modifier = Modifier
//            .fillMaxWidth()
//            .searchBarBorder(),
//            value = text,
//            onValueChange = onValueChange,
//            readOnly = readOnly,
//            leadingIcon = {
//                Icon(
//                    painter = painterResource(id = R.drawable.ic_search),
//                    contentDescription = null,
//                    modifier = Modifier.size(Dimens.IconSize),
//                    tint = colorResource(id = R.color.body)
//                )
//            },
//            placeholder = {
//                Text(
//                    text = "Search",
//                    style = MaterialTheme.typography.bodySmall,
//                    color = colorResource(
//                        id = R.color.placeholder
//                    )
//                )
//            },
//            shape = MaterialTheme.shapes.medium,
//            colors = TextFieldDefaults.textFieldColors(colorResource(id = R.color.input_background),
//                if (isSystemInDarkTheme()) Color.White else Color.Black,
//                cursorColor = if (isSystemInDarkTheme()) Color.White else Color.Black,
//                disabledIndicatorColor = Color.Transparent,
//                errorIndicatorColor = Color.Transparent,
//                focusedIndicatorColor = Color.Transparent,
//                unfocusedIndicatorColor = Color.Transparent
//            ),
//            singleLine = true,
//            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
//            keyboardActions = KeyboardActions(onSearch ={
//                onSearch()
//            }),
//            textStyle = MaterialTheme.typography.bodySmall,
//            interactionSource = interactionSource
//        )
//    }
//}

@Composable
fun SearchBar(
    text: String,
    hint: String,
    modifier: Modifier = Modifier,
    isEnabled: (Boolean) = true,
    height: Dp = 70.sdp,
    elevation: Dp = 3.sdp,
    cornerShape: Shape = RoundedCornerShape(8.sdp),
    backgroundColor: Color = Color.White,
    onSearchClicked: () -> Unit = {},
    onTextChange: (String) -> Unit = {},
    readOnly: Boolean,
    onClick: (() -> Unit)? = null,
    onValueChange: (String) -> Unit,
    onSearch: () -> Unit
) {

    val interactionSource = remember {
        MutableInteractionSource()
    }
    val isCllicked = interactionSource.collectIsPressedAsState().value
    LaunchedEffect(key1 = isCllicked) {
        if (isCllicked) {
            onClick?.invoke()
        }
    }

    var text = remember { mutableStateOf(TextFieldValue()) }

    Row(
        modifier = Modifier
            .height(height)
            .padding(horizontal = 15.sdp, vertical = 15.sdp)
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
        BasicTextField(
            modifier = modifier
                .weight(5f)
                .fillMaxWidth()
                .padding(horizontal = 24.sdp),
            value = text.value,
            onValueChange = {
                text.value = it
                onTextChange(it.text)
            },
            enabled = isEnabled,
            textStyle = TextStyle(
                color = colorResource(id = R.color.black),
                fontSize = 16.ssp,
                fontWeight = FontWeight.Bold
            ),
            decorationBox = { innerTextField ->
                if (text.value.text.isEmpty()) {
                    Text(
                        text = hint,
                        color = Color.Gray.copy(alpha = 0.5f),

                        fontWeight = FontWeight.Bold
                    )
                }
                innerTextField()
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(onSearch = { onSearch()}),
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
                    }
                }
        ) {
            if (text.value.text.isNotEmpty()) {
                Icon(
                    modifier = modifier
                        .fillMaxSize()
                        .padding(10.sdp),
                    imageVector = Icons.Default.Close,
                    contentDescription = stringResource(R.string.app_name),
                    tint = colorResource(id = R.color.black),
                )
            } else {
                Icon(
                    modifier = modifier
                        .fillMaxSize()
                        .padding(10.sdp),
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search",
                    tint = colorResource(id = R.color.black),
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun SearchBarPreview() {
    NewsAppTheme {
//        SearchBar(text = "", hint = "Search", readOnly = false, onValueChange = {}) {
//
//        }
    }
}

fun Modifier.searchBarBorder() = composed {
    if (!isSystemInDarkTheme()) {
        border(width = 1.dp, color = Color.Black, shape = MaterialTheme.shapes.medium)
    } else {
        this
    }
}