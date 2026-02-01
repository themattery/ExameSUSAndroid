package com.example.examesus.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.examesus.data.ExameRepository
import com.example.examesus.data.UserRepository
import com.example.examesus.model.Exame
import com.example.examesus.model.ExameStatus
import com.example.examesus.model.User
import com.example.examesus.model.UserType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ExameViewModel(
    private val exameRepo: ExameRepository,
    private val userRepo: UserRepository
) : ViewModel() {

    private val _exames = MutableStateFlow<List<Exame>>(emptyList())
    val exames: StateFlow<List<Exame>> = _exames

    private val _pacientes = MutableStateFlow<Map<String, User>>(emptyMap())
    val pacientes: StateFlow<Map<String, User>> = _pacientes


    fun carregarExames(unidade: String) {
        viewModelScope.launch {
            _exames.value = exameRepo.getExamesDaUnidade(unidade)
        }
    }

    fun carregarExamesCidadao(userId: String) {
        viewModelScope.launch {
            _exames.value = exameRepo.getExamesByUserId(userId)
        }
    }

    fun carregarPaciente(userId: String) {
        if (_pacientes.value.containsKey(userId)) return

        viewModelScope.launch {
            val user = userRepo.getUserById(userId)
            if (user != null) {
                _pacientes.value = _pacientes.value + (userId to user)
            }
        }
    }

    fun realizarExame(id: String) {
        viewModelScope.launch {
            exameRepo.atualizarStatus(id, ExameStatus.REALIZADO)

            _exames.value = _exames.value.map { exame ->
                if (exame.id == id)
                    exame.copy(status = ExameStatus.REALIZADO)
                else exame
            }
        }
    }

    fun cancelarExame(id: String) {
        viewModelScope.launch {
            exameRepo.atualizarStatus(id, ExameStatus.CANCELADO)

            _exames.value = _exames.value.map { exame ->
                if (exame.id == id)
                    exame.copy(status = ExameStatus.CANCELADO)
                else exame
            }
        }
    }

    fun criarExame(cpf: String, tipo: String, unidade: String, data: String = "", hora: String = "") {
        viewModelScope.launch {
            val user = userRepo.loginCidadao(cpf) ?: return@launch

            val novo = Exame(
                id = "",
                usuarioId = user.id,
                tipo = tipo,
                unidade = unidade,
                status = ExameStatus.AGENDADO,
                preparo = "",
                data = data,
                hora = hora
            )

            exameRepo.adicionar(novo)

            _exames.value = exameRepo.getExamesDaUnidade(unidade)
        }
    }

    fun criarUsuario(nome: String, cpf: String, sus: String) {
        viewModelScope.launch {
            val novo = User(
                id = System.currentTimeMillis().toString(),
                nome = nome,
                cpf = cpf,
                tipo = UserType.CIDADAO,
                cartaoSus = sus
            )
            userRepo.adicionar(novo)
        }
    }

}
