package com.code.newsapp.all.nvgraph

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.code.newsapp.R
import com.code.newsapp.all.checkinternet.interfaces.ConnectivityObserver
import com.code.newsapp.all.navigator.NewsNavigator
import com.code.newsapp.all.news.utils.Const
import com.code.newsapp.all.news.utils.Pref
import com.code.newsapp.all.news.viewmodel.SearchNewsViewModel
import com.code.newsapp.all.onboarding.OnBoardingScreen
import kotlinx.coroutines.delay

@Composable
fun NavGraph(
    navController: NavHostController,
    searchNewsViewModel: SearchNewsViewModel,
    status: State<ConnectivityObserver.Status>,
    infoDialog: MutableState<Boolean>,
) {
    NavHost(navController = navController, startDestination = "splashScreen") {

        composable(route = "splashScreen") {
            SplashScreen(navController = navController)
        }

        composable(route = "onBoarding") {
            OnBoardingScreen(navController)
        }

        composable("mainScreen") {
            NewsNavigator(
                searchNewsViewModel, status, infoDialog
            )
        }
    }
}


@Composable
fun SplashScreen(navController: NavController) {
    val scale = remember {
        Animatable(0f)
    }
    LaunchedEffect(key1 = true) {
        scale.animateTo(targetValue = 0.3f, animationSpec = tween(durationMillis = 500, easing = {
            OvershootInterpolator(2f).getInterpolation(it)
        }))
        delay(3000L)
        if (Pref.getString(Const.ONBOARDING, "") == "onBoarding") {
            navController.navigate("mainScreen") {
                popUpTo("splashScreen") {
                    inclusive = true
                }
            }
        } else {
            navController.navigate("onBoarding") {
                popUpTo("splashScreen") {
                    inclusive = true
                }
            }
        }
    }
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_logo),
            contentDescription = "Logo",
            modifier = Modifier.scale(scale.value)
        )
    }
}