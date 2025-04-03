package com.code.newsapp.all.onboarding.components

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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.code.newsapp.R
import com.code.newsapp.all.onboarding.Page
import network.chaintech.sdpcomposemultiplatform.sdp
import network.chaintech.sdpcomposemultiplatform.ssp

@Composable
fun OnBoardingPage(modifier: Modifier = Modifier, page: Page) {

    val configuration = LocalConfiguration.current
    val screenWidthDp = configuration.screenWidthDp

    val fontSize = when {
        screenWidthDp < 360 -> 12.ssp
        screenWidthDp < 400 -> 14.ssp
        else -> 16.ssp
    }

    val isTablet = configuration.screenWidthDp >= 500

    Log.e(
        "ScreenSize",
        "Width: ${configuration.screenWidthDp}, Height: ${configuration.screenHeightDp}"
    )

    val imageSize: Dp = if (isTablet) {
        300.sdp
    } else {
        400.sdp
    }

    Column(modifier = modifier) {
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .height(imageSize),
            painter = painterResource(id = page.image),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = page.tittle,
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 8.dp),
            style = TextStyle(
                lineHeight = 0.sp,
                platformStyle = PlatformTextStyle(includeFontPadding = false)
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
                lineHeight = fontSize,
                platformStyle = PlatformTextStyle(includeFontPadding = false)
            )
        )
    }
}