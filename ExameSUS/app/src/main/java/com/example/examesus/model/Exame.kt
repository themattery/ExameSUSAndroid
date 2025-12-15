package com.example.examesus.model

enum class ExameStatus {
    AGENDADO,
    EM_ANDAMENTO,
    REALIZADO,
    CANCELADO
}

data class Exame (
    val id: String,
    val usuarioId: String,
    val tipo: String,
    val unidade: String,
    val status: ExameStatus,
    val preparo: String
)