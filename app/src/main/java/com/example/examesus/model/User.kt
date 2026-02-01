package com.example.examesus.model

enum class UserType {
    CIDADAO,
    SERVIDOR
}


data class User(
    val id: String = "",
    val nome: String = "",
    val cpf: String = "",
    val tipo: UserType = UserType.CIDADAO,
    val cartaoSus: String = "",
    val unidadeSaude: String = ""
)
