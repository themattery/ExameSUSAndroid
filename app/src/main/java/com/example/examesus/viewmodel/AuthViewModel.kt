package com.example.examesus.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.examesus.data.UserRepository
import com.example.examesus.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class AuthViewModel(
    private val userRepository: UserRepository
) : ViewModel() {

    // UI State (Padrão UI State - como requisitado)
    sealed class LoginUIState {
        object Idle : LoginUIState()
        object Loading : LoginUIState()
        data class Success(val user: User) : LoginUIState()
        data class Error(val message: String? = null) : LoginUIState()
    }

    private val _uiState = MutableStateFlow<LoginUIState>(LoginUIState.Idle)
    val uiState: StateFlow<LoginUIState> = _uiState

    /** Usuário logado — derivado do uiState quando está em Success */
    val loggedUser: StateFlow<User?> = _uiState
        .map { state -> (state as? LoginUIState.Success)?.user }
        .stateIn(viewModelScope, SharingStarted.Eagerly, null)

    fun clearError() {
        _uiState.value = LoginUIState.Idle
    }

    fun loginCidadao(cpf: String) {
        if (cpf.isBlank()) {
            _uiState.value = LoginUIState.Error("CPF inválido")
            return
        }

        viewModelScope.launch {
            _uiState.value = LoginUIState.Loading

            val user = userRepository.loginCidadao(cpf)

            _uiState.value = if (user != null) {
                LoginUIState.Success(user)
            } else {
                LoginUIState.Error("Usuário não encontrado")
            }
        }
    }

    fun loginServidor(id: String, nome: String, unidade: String) {
        if (id.isBlank() || nome.isBlank() || unidade.isBlank()) {
            _uiState.value = LoginUIState.Error("Preencha todos os campos")
            return
        }

        viewModelScope.launch {
            _uiState.value = LoginUIState.Loading

            val user = userRepository.loginServidor(id, nome, unidade)

            _uiState.value = if (user != null) {
                LoginUIState.Success(user)
            } else {
                LoginUIState.Error("Servidor não encontrado")
            }
        }
    }

    fun logout() {
        _uiState.value = LoginUIState.Idle
    }
}
