package com.example.architectcoderspracticauno.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.architectcoderspracticauno.data.model.Wizard
import com.example.architectcoderspracticauno.data.repository.HogwartsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel: ViewModel() {
    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state.asStateFlow()

    private val _showWelcomeToast = MutableStateFlow(false)
    val showWelcomeToast: StateFlow<Boolean> = _showWelcomeToast

    private val repository = HogwartsRepository()

    fun showWelcomeToast() {
        _showWelcomeToast.value = true
    }

    fun loadWizardsByHouse(house: String){
        viewModelScope.launch {
            _state.value = UiState(repository.getWizardsSortedByHouse(house))
        }
    }

    data class UiState(
        val wizards: List<Wizard> = emptyList()
    )
}