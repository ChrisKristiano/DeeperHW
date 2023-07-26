package com.example.deeperhw.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.deeperhw.common.RepoResult
import com.example.deeperhw.domain.usecase.PerformLoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val performLogin: PerformLoginUseCase
) : ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _onLoginSuccess = MutableSharedFlow<Unit>()
    val onLoginSuccess = _onLoginSuccess.asSharedFlow()

    private val _onLoginError = MutableSharedFlow<Unit>()
    val onLoginError = _onLoginError.asSharedFlow()

    fun login(email: String, password: String) {
        performLogin(email, password).onEach { result ->
            when (result) {
                is RepoResult.Error -> {
                    _isLoading.emit(false)
                    _onLoginError.emit(Unit)
                }
                is RepoResult.Loading -> {
                    _isLoading.emit(true)
                }
                is RepoResult.Success -> {
                    _onLoginSuccess.emit(Unit)
                }
            }
        }.launchIn(viewModelScope)
    }
}