package com.example.examesus.data

import com.example.examesus.model.*
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

object ExameRepository {

    private val db = FirebaseFirestore.getInstance()
    private val examesRef = db.collection("exames")

    suspend fun adicionar(exame: Exame) {
        examesRef.add(
            mapOf(
                "usuarioId" to exame.usuarioId,
                "tipo" to exame.tipo,
                "unidade" to exame.unidade,
                "status" to exame.status.name,
                "preparo" to exame.preparo
            )
        ).await()
    }

    suspend fun getExamesByUserId(userId: String): List<Exame> {
        val snapshot = examesRef
            .whereEqualTo("usuarioId", userId)
            .get()
            .await()

        return snapshot.documents.map { doc ->
            Exame(
                id = doc.id,
                usuarioId = doc.getString("usuarioId") ?: "",
                tipo = doc.getString("tipo") ?: "",
                unidade = doc.getString("unidade") ?: "",
                status = ExameStatus.valueOf(
                    doc.getString("status") ?: ExameStatus.AGENDADO.name
                ),
                preparo = doc.getString("preparo") ?: ""
            )
        }
    }

    suspend fun getExamesDaUnidade(unidade: String): List<Exame> {
        val snapshot = examesRef
            .whereEqualTo("unidade", unidade)
            .get()
            .await()

        return snapshot.documents.map { doc ->
            Exame(
                id = doc.id,
                usuarioId = doc.getString("usuarioId") ?: "",
                tipo = doc.getString("tipo") ?: "",
                unidade = doc.getString("unidade") ?: "",
                status = ExameStatus.valueOf(
                    doc.getString("status") ?: ExameStatus.AGENDADO.name
                ),
                preparo = doc.getString("preparo") ?: ""
            )
        }
    }

    suspend fun atualizarStatus(id: String, status: ExameStatus) {
        examesRef.document(id)
            .update("status", status.name)
            .await()
    }
}
