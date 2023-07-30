package com.example.deeperhw.presentation.scandetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.deeperhw.common.RepoResult
import com.example.deeperhw.domain.usecase.GetScanDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.json.JSONObject
import javax.inject.Inject

@HiltViewModel
class ScanDetailsViewModel @Inject constructor(
    private val getScanDetails: GetScanDetailsUseCase
) : ViewModel() {

    private val _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()

    private val _scanDetails = MutableStateFlow(JSONObject())
    val scanDetails = _scanDetails.asStateFlow()

    private val _onError = MutableSharedFlow<Unit>()
    val onError = _onError.asSharedFlow()

    fun load(scanId: Int) {
        getScanDetails(scanId).onEach {
            when (it) {
                is RepoResult.Error -> {
                    _onError.emit(Unit)
                    _isLoading.emit(false)
                }
                is RepoResult.Loading -> {
                    _isLoading.emit(true)
                }
                is RepoResult.Success -> {
                    it.data?.let {
                        _scanDetails.emit(it)
                    } ?: run {
                        _onError.emit(Unit)
                    }
                    _isLoading.emit(false)
                }
            }
        }.launchIn(viewModelScope)
    }
}