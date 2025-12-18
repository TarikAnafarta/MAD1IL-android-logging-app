package com.fhhgb.loggingapp

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ShowcaseViewModel: ViewModel() {

    data class UiState(
        val showWeb: Boolean = false,
        val showPhone: Boolean = false,
        val showMap: Boolean = false
    )

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    sealed interface Action{
        data class OpenWeb(val shouldOpen: Boolean): Action
        data class OpenPhone(val shouldOpen: Boolean): Action
        data class OpenMap(val shouldOpen: Boolean): Action
    }

    fun dispatch(action: Action) {
        when (action) {
            is Action.OpenMap -> {
                _uiState.value = _uiState.value.copy(showMap = action.shouldOpen)
            }
            is Action.OpenPhone -> {
                _uiState.value = _uiState.value.copy(showPhone = action.shouldOpen)
            }
            is Action.OpenWeb -> {
                _uiState.value = _uiState.value.copy(showWeb = action.shouldOpen)
            }
        }
    }
}