package com.code.newsapp

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.code.newsapp.domain.usecases.app_entry.AppEntryUseCases
import com.code.newsapp.presentation.nvgraph.Route
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val appEntryUseCases: AppEntryUseCases) :
    ViewModel() {

    var splashCondition = mutableStateOf(true)
        private set

    var startDestination = mutableStateOf(Route.SplashNavigatorScreen.route)
        private set

    init {
        appEntryUseCases.readAppEntry().onEach { shouldStartFromHomeScreen ->
            if (shouldStartFromHomeScreen) {
                startDestination.value = Route.NewsNavigation.route
            } else {
                startDestination.value = Route.SplashNavigatorScreen.route
            }
            splashCondition.value = false
        }.launchIn(viewModelScope)
    }
}