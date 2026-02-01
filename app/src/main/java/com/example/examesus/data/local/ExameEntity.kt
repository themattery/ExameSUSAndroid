package com.example.examesus.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.examesus.model.Exame
import com.example.examesus.model.ExameStatus


@Entity(tableName = "exames")
data class ExameEntity(
    @PrimaryKey
    val id: String,
    val usuarioId: String,
    val tipo: String,
    val unidade: String,
    val status: String,  // ExameStatus.name
    val preparo: String,
    val data: String = "",
    val hora: String = ""
) {
    fun toModel(): Exame = Exame(
        id = id,
        usuarioId = usuarioId,
        tipo = tipo,
        unidade = unidade,
        status = ExameStatus.valueOf(status),
        preparo = preparo,
        data = data,
        hora = hora
    )

    companion object {
        fun fromModel(exame: Exame) = ExameEntity(
            id = exame.id,
            usuarioId = exame.usuarioId,
            tipo = exame.tipo,
            unidade = exame.unidade,
            status = exame.status.name,
            preparo = exame.preparo,
            data = exame.data,
            hora = exame.hora
        )
    }
}
