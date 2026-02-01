package com.example.examesus.data

import com.example.examesus.data.local.ExameDao
import com.example.examesus.data.local.ExameEntity
import com.example.examesus.model.*
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await


class ExameRepository(
    private val exameDao: ExameDao
) {

    private val db = FirebaseFirestore.getInstance()
    private val examesRef = db.collection("exames")

    suspend fun adicionar(exame: Exame) {
        val docRef = examesRef.add(
            mapOf(
                "usuarioId" to exame.usuarioId,
                "tipo" to exame.tipo,
                "unidade" to exame.unidade,
                "status" to exame.status.name,
                "preparo" to exame.preparo,
                "data" to exame.data,
                "hora" to exame.hora
            )
        ).await()
        // Atualiza cache local com o exame criado (id vem do Firestore)
        val exameComId = exame.copy(id = docRef.id)
        exameDao.insert(ExameEntity.fromModel(exameComId))
    }

    suspend fun getExamesByUserId(userId: String): List<Exame> {
        return try {
            val snapshot = examesRef
                .whereEqualTo("usuarioId", userId)
                .get()
                .await()

            val exames = snapshot.documents.map { doc -> docToExame(doc) }
            // Atualiza cache Room com dados do Firebase
            exameDao.deleteByUserId(userId)
            exameDao.insertAll(exames.map { ExameEntity.fromModel(it) })
            exames
        } catch (e: Exception) {
            // Offline ou erro de rede: retorna do cache Room
            exameDao.getExamesByUserId(userId).map { it.toModel() }
        }
    }

    private fun docToExame(doc: com.google.firebase.firestore.DocumentSnapshot): Exame = Exame(
        id = doc.id,
        usuarioId = doc.getString("usuarioId") ?: "",
        tipo = doc.getString("tipo") ?: "",
        unidade = doc.getString("unidade") ?: "",
        status = ExameStatus.valueOf(
            doc.getString("status") ?: ExameStatus.AGENDADO.name
        ),
        preparo = doc.getString("preparo") ?: "",
        data = doc.getString("data") ?: "",
        hora = doc.getString("hora") ?: ""
    )

    suspend fun getExamesDaUnidade(unidade: String): List<Exame> {
        return try {
            val snapshot = examesRef
                .whereEqualTo("unidade", unidade)
                .get()
                .await()

            val exames = snapshot.documents.map { doc -> docToExame(doc) }
            // Atualiza cache Room
            exameDao.deleteByUnidade(unidade)
            exameDao.insertAll(exames.map { ExameEntity.fromModel(it) })
            exames
        } catch (e: Exception) {
            // Offline: retorna do cache Room
            exameDao.getExamesDaUnidade(unidade).map { it.toModel() }
        }
    }

    suspend fun atualizarStatus(id: String, status: ExameStatus) {
        examesRef.document(id)
            .update("status", status.name)
            .await()
        // Sincroniza cache local
        exameDao.updateStatus(id, status.name)
    }
}
