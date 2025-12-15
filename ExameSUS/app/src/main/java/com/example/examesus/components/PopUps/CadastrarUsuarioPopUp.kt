package com.example.examesus.components.PopUps

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CadastrarUsuarioPopUp(
    onDismiss: () -> Unit,
    onConfirm: (nome: String, cpf: String, sus: String) -> Unit
) {
    var nome by remember { mutableStateOf("") }
    var cpf by remember { mutableStateOf("") }
    var sus by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Cadastrar Usuário") },
        text = {
            Column {
                OutlinedTextField(value = nome, onValueChange = { nome = it }, label = { Text("Nome") })
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(value = cpf, onValueChange = { cpf = it }, label = { Text("CPF") })
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(value = sus, onValueChange = { sus = it }, label = { Text("Cartão SUS") })
            }
        },
        confirmButton = {
            TextButton(onClick = { onConfirm(nome, cpf, sus) }) {
                Text("Cadastrar")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )
}
