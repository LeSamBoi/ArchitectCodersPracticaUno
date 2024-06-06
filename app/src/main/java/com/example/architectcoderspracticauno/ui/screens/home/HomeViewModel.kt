package com.example.architectcoderspracticauno.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.architectcoderspracticauno.data.model.toWizardModel
import com.example.architectcoderspracticauno.data.repository.HogwartsRepository
import com.example.architectcoderspracticauno.ui.model.WizardModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

private const val DEFAULT_HOUSE = "gryffindor"

class HomeViewModel: ViewModel() {
    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state.asStateFlow()

    private val _showedWelcomeToast = MutableStateFlow(false)
    val showedWelcomeToast: StateFlow<Boolean> = _showedWelcomeToast

    private val repository = HogwartsRepository()

    fun showedWelcomeToast() {
        _showedWelcomeToast.value = true
    }

    init {
        loadWizardsByHouse(DEFAULT_HOUSE)
    }

    fun loadWizardsByHouse(house: String){
        viewModelScope.launch {
            val wizards = repository.getWizardsSortedByHouse(house).map { it.toWizardModel() }
            _state.value = UiState(wizards = wizards, selectedHouse = house)
        }
    }

    data class UiState(
        val wizards: List<WizardModel> = emptyList(),
        val selectedHouse: String = "gryffindor"
    )
}