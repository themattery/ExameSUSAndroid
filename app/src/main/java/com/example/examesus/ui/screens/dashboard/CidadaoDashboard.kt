package com.example.examesus.ui.screens.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.SimpleDateFormat
import java.util.Locale
import org.koin.androidx.compose.koinViewModel
import com.example.examesus.components.UserInfoCard
import com.example.examesus.model.Exame
import com.example.examesus.model.ExameStatus
import com.example.examesus.model.User
import com.example.examesus.viewmodel.ExameViewModel

private fun formatarDataExame(isoDate: String): String {
    return try {
        val input = SimpleDateFormat("yyyy-MM-dd", Locale.US)
        val output = SimpleDateFormat("dd/MM/yyyy", Locale("pt", "BR"))
        output.format(input.parse(isoDate)!!)
    } catch (e: Exception) {
        isoDate
    }
}

@Composable
fun CidadaoDashboard(
    user: User,
    viewModel: ExameViewModel = koinViewModel()
) {
    val exames by viewModel.exames.collectAsState()

    LaunchedEffect(user.id) {
        viewModel.carregarExamesCidadao(user.id)
    }

    val proximos = exames.filter { it.status == ExameStatus.AGENDADO }
    val historico = exames.filter { it.status != ExameStatus.AGENDADO }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 32.dp)
    ) {
        item {
            UserInfoCard(
                nome = user.nome,
                tipo = "Cidadão",
                cpf = user.cpf,
                cartaoSus = user.cartaoSus
            )
        }

        item {
            ProximosExamesSection(proximos)
        }

        item {
            HistoricoExamesSection(historico)
        }
    }
}

@Composable
fun ProximosExamesSection(exames: List<Exame>) {
    if (exames.isEmpty()) {
        Text(
            "Nenhum exame agendado.",
            modifier = Modifier.padding(16.dp)
        )
        return
    }

    Column(Modifier.padding(16.dp)) {
        Text("Próximos Exames", fontSize = 20.sp, fontWeight = FontWeight.Bold)

        Spacer(Modifier.height(8.dp))

        exames.forEach { exame ->
            ExameItemSimples(exame)
            Spacer(Modifier.height(12.dp))
        }
    }
}

@Composable
fun HistoricoExamesSection(exames: List<Exame>) {
    if (exames.isEmpty()) return

    Column(Modifier.padding(16.dp)) {
        Text("Histórico", fontSize = 20.sp, fontWeight = FontWeight.Bold)

        Spacer(Modifier.height(8.dp))

        exames.forEach { exame ->
            ExameItemSimples(exame)
            Spacer(Modifier.height(12.dp))
        }
    }
}

@Composable
fun ExameItemSimples(exame: Exame) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp)
            .background(Color.White)
            .padding(16.dp)
    ) {
        Text(
            exame.tipo,
            fontWeight = FontWeight.Bold,
            fontSize = 17.sp
        )

        Spacer(Modifier.height(4.dp))

        Text("Unidade: ${exame.unidade}")

        if (exame.data.isNotBlank() || exame.hora.isNotBlank()) {
            Spacer(Modifier.height(4.dp))
            val dataHora = buildString {
                if (exame.data.isNotBlank()) append(formatarDataExame(exame.data))
                if (exame.data.isNotBlank() && exame.hora.isNotBlank()) append(" às ")
                if (exame.hora.isNotBlank()) append(exame.hora)
            }
            Text("Data/Hora: $dataHora")
        }

        Spacer(Modifier.height(4.dp))

        Text("Status: ${exame.status.name}")
    }
}




