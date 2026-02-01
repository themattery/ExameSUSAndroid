package com.example.examesus.data

import com.example.examesus.model.User
import com.example.examesus.model.UserType
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class UserRepository {

    private val db = FirebaseFirestore.getInstance()
    private val usersCollection = db.collection("users")

    suspend fun adicionar(user: User) {
        usersCollection
            .document(user.id)
            .set(user)
            .await()
    }

    suspend fun loginCidadao(doc: String): User? {
        val snapshot = usersCollection
            .whereEqualTo("tipo", UserType.CIDADAO.name)
            .get()
            .await()

        return snapshot.documents
            .mapNotNull { it.toObject(User::class.java) }
            .find { it.cpf == doc || it.cartaoSus == doc }
    }

    suspend fun loginServidor(
        id: String,
        nome: String,
        unidade: String
    ): User? {
        val snapshot = usersCollection
            .whereEqualTo("tipo", UserType.SERVIDOR.name)
            .whereEqualTo("id", id)
            .whereEqualTo("nome", nome)
            .whereEqualTo("unidadeSaude", unidade)
            .get()
            .await()

        return snapshot.documents
            .firstOrNull()
            ?.toObject(User::class.java)
    }

    suspend fun getUserById(id: String): User? {
        val doc = usersCollection
            .document(id)
            .get()
            .await()

        return doc.toObject(User::class.java)
    }
}
