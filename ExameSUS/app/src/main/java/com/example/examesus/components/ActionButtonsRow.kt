package com.example.examesus.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ActionButtonsRow(
    onAgendarClick: () -> Unit,
    onCadastrarClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
        
    ) {
        ActionButton(
            text = "Agendar Exame",
            description = "Agendar para paciente",
            color = Color(0xFF1E88E5),
            onClick = onAgendarClick,
            icon = Icons.Default.DateRange
        )

        ActionButton(
            text = "Cadastrar Usuário",
            description = "Novo cidadão no SUS",
            color = Color(0xFF2E7D32),
            onClick = onCadastrarClick,
            icon = Icons.Default.PersonAdd
        )
    }
}

@Composable
fun ActionButton(
    text: String,
    description: String,
    color: Color,
    onClick: () -> Unit,
    icon: ImageVector
) {
    Surface(
        modifier = Modifier
            .height(90.dp)
            .clickable { onClick() },
        color = color,
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(12.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Icon(icon, contentDescription = null, tint = Color.White)
            Column {
                Text(text, color = Color.White, fontWeight = FontWeight.Bold)
                Text(description, color = Color.White.copy(alpha = 0.8f), fontSize = 12.sp)
            }
        }
    }
}

@Preview
@Composable
fun ActionButtonsRowPreview() {
    ActionButtonsRow(
        onAgendarClick = { },
        onCadastrarClick = { }
    )
}
