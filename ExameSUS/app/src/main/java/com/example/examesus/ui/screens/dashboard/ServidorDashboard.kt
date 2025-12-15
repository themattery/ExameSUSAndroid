package com.example.examesus.ui.screens.dashboard

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.examesus.components.ActionButtonsRow
import com.example.examesus.components.PopUps.AgendarExamePopUp
import com.example.examesus.components.PopUps.CadastrarUsuarioPopUp
import com.example.examesus.components.UserInfoCard
import com.example.examesus.model.User
import com.example.examesus.model.UserType
import com.example.examesus.ui.screens.exame.ExamesListCard
import com.example.examesus.ui.theme.ExameSUSTheme
import com.example.examesus.viewmodel.ExameViewModel


@Composable
fun ServidorDashboard(
    user: User,
    viewModel: ExameViewModel = viewModel()
) {

    val exames by viewModel.exames.collectAsState()
    var showPopUpAgendar by remember { mutableStateOf(false) }
    var showPopUpCadastrar by remember { mutableStateOf(false) }


    LaunchedEffect((user.unidadeSaude)) {
        user.unidadeSaude.let {viewModel.carregarExames(it)}
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 32.dp)
    ) {

        // Card inicial
        item {
            UserInfoCard(
                nome = user.nome,
                tipo = "Servidor",
                unidade = user.unidadeSaude
            )
        }

        // Botões agendar/cadastrar
        item {
            ActionButtonsRow(
                onAgendarClick = { showPopUpAgendar = true },
                onCadastrarClick = { showPopUpCadastrar = true }
            )
        }

        // Lista de exames
        item {
            ExamesListCard(
                exames,
                viewModel = viewModel
            )
        }
    }
    if (showPopUpAgendar) {
        AgendarExamePopUp(
            onDismiss = { showPopUpAgendar = false },
            onConfirm = { cpf, tipo ->
                viewModel.criarExame(cpf, tipo, unidade = user.unidadeSaude)
                showPopUpAgendar = false
            }
        )
    }

    if (showPopUpCadastrar) {
        CadastrarUsuarioPopUp(
            onDismiss = { showPopUpCadastrar = false },
            onConfirm = { nome, cpf, sus ->
                viewModel.criarUsuario(nome, cpf, sus)
                showPopUpCadastrar = false
            }
        )
    }

}


@Preview(showBackground = true)
@Composable
fun ServidorPreview() {
    ExameSUSTheme {
        ServidorDashboard(user = User(
            id = "2",
            cpf = "0201",
            nome = "Servidor Teste",
            tipo = UserType.SERVIDOR,
            unidadeSaude = "UBS Centro"
        ))
    }
}