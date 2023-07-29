package com.example.deeperhw.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.deeperhw.common.RepoResult
import com.example.deeperhw.domain.model.Scan
import com.example.deeperhw.domain.usecase.GetScannedLocationsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getScannedLocations: GetScannedLocationsUseCase
) : ViewModel() {

    private val _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()

    private val _scannedLocations = MutableStateFlow<List<Scan>>(emptyList())
    val scannedLocations = _scannedLocations.asStateFlow()

    private val _onError = MutableSharedFlow<Unit>()
    val onError = _onError.asSharedFlow()

    init {
        getScannedLocations().onEach {
            when(it) {
                is RepoResult.Error -> {
                    _onError.emit(Unit)
                    _isLoading.emit(false)
                }
                is RepoResult.Loading -> {
                    _isLoading.emit(true)
                }
                is RepoResult.Success -> {
                    _scannedLocations.emit(it.data ?: emptyList())
                    _isLoading.emit(false)
                }
            }
        }.launchIn(viewModelScope)
    }
}