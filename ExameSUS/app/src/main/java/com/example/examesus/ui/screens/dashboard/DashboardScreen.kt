package com.example.examesus.ui.screens.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.examesus.components.DashboardTopBar
import com.example.examesus.model.User
import com.example.examesus.model.UserType
import com.example.examesus.ui.theme.ExameSUSTheme

@Composable
fun DashboardScreen(
    user: User,
    onLogout: () -> Unit = {},
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF3F5F7))
    ) {

        DashboardTopBar(onLogout)

        when (user.tipo) {
            UserType.CIDADAO -> CidadaoDashboard(user)
            UserType.SERVIDOR -> ServidorDashboard(user)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DashPreview() {
    ExameSUSTheme {
        DashboardScreen(user = User(
            id = "2",
            cpf = "0101",
            nome = "Servidor Teste",
            tipo = UserType.CIDADAO,
            unidadeSaude = "UBS Centro"
        ))
    }
}