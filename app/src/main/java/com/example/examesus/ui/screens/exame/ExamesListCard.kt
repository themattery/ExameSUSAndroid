package com.example.examesus.ui.screens.exame

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.examesus.model.Exame
import com.example.examesus.viewmodel.ExameViewModel
import androidx.compose.runtime.getValue

@Composable
fun ExamesListCard(
    exames: List<Exame>,
    viewModel: ExameViewModel
) {
    val pacientes by viewModel.pacientes.collectAsState()
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(12.dp),
        tonalElevation = 2.dp,
        shadowElevation = 2.dp,
        color = Color.White
    ) {
        Column(Modifier.padding(16.dp)) {

            Text("Exames da Unidade", fontWeight = FontWeight.Bold, fontSize = 18.sp)

            Spacer(Modifier.height(16.dp))

            exames.forEach { exame ->

                LaunchedEffect(exame.usuarioId) {
                    viewModel.carregarPaciente(exame.usuarioId)
                }

                val paciente = pacientes[exame.usuarioId]


                val nome = paciente?.nome ?: "---"
                val cpf = paciente?.cpf ?: "---"

                ExameItem(
                    exame = exame,
                    pacienteNome = nome,
                    pacienteCpf = cpf,
                    onRealizado = viewModel::realizarExame,
                    onCancelar = viewModel::cancelarExame
                )
                HorizontalDivider()
            }


        }
    }
}
