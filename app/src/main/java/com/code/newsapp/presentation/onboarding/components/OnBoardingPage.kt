package com.code.newsapp.presentation.onboarding.components

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.code.newsapp.R
import com.code.newsapp.presentation.Dimens.MediumPadding1
import com.code.newsapp.presentation.onboarding.Page
import com.code.newsapp.presentation.onboarding.pages
import com.code.newsapp.ui.theme.NewsAppTheme
import network.chaintech.sdpcomposemultiplatform.sdp
import network.chaintech.sdpcomposemultiplatform.ssp

@Composable
fun OnBoardingPage(modifier: Modifier = Modifier, page: Page) {

    val configuration = LocalConfiguration.current
    val screenWidthDp = configuration.screenWidthDp


    val fontSize = when {
        screenWidthDp < 360 -> 12.ssp  // Small screens
        screenWidthDp < 400 -> 14.ssp  // Normal screens
        else -> 16.ssp  // Tablets and large devices
    }

    val isTablet = configuration.screenWidthDp >= 500

    Log.e("ScreenSize", "Width: ${configuration.screenWidthDp}, Height: ${configuration.screenHeightDp}")

    val imageSize: Dp = if (isTablet) {
        300.sdp
    } else {
        400.sdp
    }


    Column(modifier = modifier) {
        Image(
            modifier = Modifier
                .fillMaxWidth().height(imageSize),
            painter = painterResource(id = page.image),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(MediumPadding1))
        Text(
            text = page.tittle,
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 8.dp),
            style = TextStyle(
                lineHeight = 0.sp,
                platformStyle = PlatformTextStyle(includeFontPadding = false) // Removes font padding
            ),
            color = colorResource(id = R.color.display_small),
            fontSize = fontSize,

            )
        Text(
            text = page.description,
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 8.dp),
            color = colorResource(id = R.color.text_medium),
            fontSize = fontSize,
            style = TextStyle(
                lineHeight = fontSize, // Set line height equal to font size
                platformStyle = PlatformTextStyle(includeFontPadding = false) // Remove font padding
            )
        )
    }
}

@Preview(showBackground = true)
@Preview(uiMode = UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun OnBoardingPagePreview() {
    NewsAppTheme {
        OnBoardingPage(page = pages[0])
    }
}