package com.example.examesus.components

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*

@Composable
fun DashboardTopBar(onLogout: () -> Unit) {
    Surface(
        color = Color.White,
        tonalElevation = 2.dp,
        shadowElevation = 2.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text("ExameSUS", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                Text(
                    "Sistema de Acompanhamento de Exames",
                    fontSize = 13.sp,
                    color = Color.Gray
                )
            }

            Text(
                "Sair",
                color = Color(0xFF1E88E5),
                modifier = Modifier.clickable { onLogout() }
            )
        }
    }
}
