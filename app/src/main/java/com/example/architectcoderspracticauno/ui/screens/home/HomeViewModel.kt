package com.example.architectcoderspracticauno.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.architectcoderspracticauno.data.repository.HogwartsRepository
import com.example.architectcoderspracticauno.ui.common.Result
import com.example.architectcoderspracticauno.ui.common.capitalize
import com.example.architectcoderspracticauno.ui.model.WizardModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

private const val DEFAULT_HOUSE = "gryffindor"

class HomeViewModel(
    private val repository: HogwartsRepository
): ViewModel() {

    private val _showedWelcomeToast = MutableStateFlow(false)
    val showedWelcomeToast: StateFlow<Boolean> = _showedWelcomeToast

    private val _selectedHouse = MutableStateFlow(DEFAULT_HOUSE)

    val state: StateFlow<Result<UiState>>
    val favoriteWizards: StateFlow<Result<List<WizardModel>>>

    init {
        @OptIn(ExperimentalCoroutinesApi::class)
        state = _selectedHouse
            .flatMapLatest { house ->
                repository.fetchWizardsByHouse(house.capitalize())
                    .map { wizards ->
                        UiState(wizards = wizards, selectedHouse = house)
                    }
                    .map<UiState, Result<UiState>> { Result.Success(it) }
                    .catch { e -> emit(Result.Error(e)) }
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = Result.Success(UiState())
            )

        favoriteWizards = repository.fetchFavoriteWizards()
            .map<List<WizardModel>, Result<List<WizardModel>>> { Result.Success(it) }
            .catch { e -> emit(Result.Error(e)) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = Result.Success(emptyList())
            )
    }

    fun loadWizardsByHouse(house: String){
        _selectedHouse.value = house
    }

    fun setShowedWelcomeToast() {
        _showedWelcomeToast.value = true
    }

    data class UiState(
        val wizards: List<WizardModel> = emptyList(),
        val selectedHouse: String = DEFAULT_HOUSE,
        val error : String = ""
    )
}