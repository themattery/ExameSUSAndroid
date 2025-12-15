package com.example.examesus.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.examesus.data.UserRepository
import com.example.examesus.model.User
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {

    private val userRepository = UserRepository

    var loggedUser by mutableStateOf<User?>(null)
        private set


    fun loginCidadao(cpf: String) {
        if (cpf.isBlank()) {
            return
        }

        viewModelScope.launch {
            val user = userRepository.loginCidadao(cpf)

            if (user != null) {
                loggedUser = user
            }
        }
    }

    fun loginServidor(id: String, nome: String, unidade: String) {
        if (id.isBlank() || nome.isBlank() || unidade.isBlank()) {
            return
        }

        viewModelScope.launch {
            val user = userRepository.loginServidor(id, nome, unidade)

            if (user != null) {
                loggedUser = user
            }
        }
    }

    fun logout() {
        loggedUser = null
    }
}


