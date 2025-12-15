package com.example.examesus.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*

@Composable
fun UserInfoCard(
    nome: String,
    tipo: String,
    unidade: String? = null,
    cpf: String? = null,
    cartaoSus: String? = null
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        color = Color.White,
        shape = RoundedCornerShape(12.dp),
        tonalElevation = 2.dp,
        shadowElevation = 2.dp
    ) {

        if (tipo == "Servidor") {
            // card do servidor
            Column(modifier = Modifier.padding(16.dp)) {

                Text(
                    text = "Painel do Servidor",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )

                Spacer(Modifier.height(8.dp))

                Text("Servidor: $nome")

                unidade?.let {
                    Text("Unidade: $it")
                }
            }

        } else {
        // card cidadão
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {


                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Olá, $nome!",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )

                    Spacer(Modifier.height(6.dp))

                    Text(
                        text = "Acompanhe seus exames e mantenha-se\ninformado sobre sua saúde.",
                        fontSize = 11.sp,
                        color = Color.DarkGray
                    )
                }

                Column(modifier = Modifier.padding(start = 5.dp)) {

                    cpf?.let {
                        Text("CPF: $it", fontSize = 10.sp)
                    }

                    cartaoSus?.let {
                        Spacer(Modifier.height(4.dp))
                        Text("Cartão SUS: $it", fontSize = 10.sp)
                    }
                }
            }
        }
    }
}

