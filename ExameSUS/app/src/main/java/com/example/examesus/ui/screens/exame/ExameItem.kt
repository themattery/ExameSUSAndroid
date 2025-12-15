package com.example.examesus.ui.screens.exame

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.examesus.model.Exame
import com.example.examesus.model.ExameStatus

@Composable
fun ExameItem(
    exame: Exame,
    pacienteNome: String,
    pacienteCpf: String,
    onRealizado: (String) -> Unit,
    onCancelar: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp)
            .background(Color.White)
    ) {
        Text(exame.tipo, fontWeight = FontWeight.Bold, fontSize = 17.sp)

        Text("Paciente: $pacienteNome")
        Text("CPF: $pacienteCpf")

//        Spacer(Modifier.height(12.dp))

//        Surface(
//            shape = RoundedCornerShape(10.dp),
//            color = Color(0xFFFFF8E1),
//            border = BorderStroke(1.dp, Color(0xFFFFECB3))
//        ) {
//            Text(
//                "Preparo: ${exame.preparo}",
//                modifier = Modifier.padding(12.dp)
//            )
//        }

        Spacer(Modifier.height(12.dp))

        if (exame.status == ExameStatus.AGENDADO) {
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                ActionButton(
                    text = "Realizado",
                    color = Color(0xFF2E7D32)
                ) { onRealizado(exame.id) }

                ActionButton(
                    text = "Cancelar",
                    color = Color(0xFFC62828)
                ) { onCancelar(exame.id) }
            }
        } else {
            StatusCoiso(exame.status)
        }

    }
}

@Composable
fun ActionButton(
    text: String,
    color: Color,
    onClick: () -> Unit
) {
    Surface(
        color = color,
        shape = RoundedCornerShape(50),
        modifier = Modifier
            .height(32.dp)
            .clickable { onClick() }
    ) {
        Box(
            Modifier.padding(horizontal = 14.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(text, color = Color.White)
        }
    }
}

@Composable
fun StatusCoiso(status: ExameStatus) {
    val (label, color) = when (status) {
        ExameStatus.REALIZADO -> "Realizado" to Color(0xFF2E7D32)
        ExameStatus.CANCELADO -> "Cancelado" to Color(0xFFC62828)
        ExameStatus.EM_ANDAMENTO -> "Em andamento" to Color(0xFFFFA000)
        ExameStatus.AGENDADO -> "Agendado" to Color(0xFF1976D2)
    }

    Surface(
        color = color.copy(alpha = 0.1f),
        border = BorderStroke(1.dp, color),
        shape = RoundedCornerShape(8.dp)
    ) {
        Text(
            label,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
            color = color,
            fontWeight = FontWeight.Bold
        )
    }
}
