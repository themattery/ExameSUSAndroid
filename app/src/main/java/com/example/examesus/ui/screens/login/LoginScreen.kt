package com.example.examesus.ui.screens.login

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.examesus.di.appModule
import org.koin.compose.KoinApplication
import org.koin.androidx.compose.koinViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.examesus.viewmodel.AuthViewModel


enum class LoginType { CIDADAO, SERVIDOR }

@Composable
fun LoginScreen(
    navController: NavController,
    authViewModel: AuthViewModel
) {
    val uiState by authViewModel.uiState.collectAsState()
    val loggedUser by authViewModel.loggedUser.collectAsState()

    var loginType by rememberSaveable { mutableStateOf(LoginType.CIDADAO) }
    var cidadaoCpf by rememberSaveable { mutableStateOf("") }
    var servidorId by rememberSaveable { mutableStateOf("") }
    var servidorNome by rememberSaveable { mutableStateOf("") }
    var servidorUnidade by rememberSaveable { mutableStateOf("") }
    var unidadeExpanded by remember { mutableStateOf(false) }

    val isLoading = uiState is AuthViewModel.LoginUIState.Loading

    // Navega para o dashboard quando o login for bem-sucedido
    LaunchedEffect(loggedUser) {
        if (loggedUser != null) {
            navController.navigate("dashboard")
        }
    }

    // Snackbar para feedback de erro
    val snackbarHostState = remember { SnackbarHostState() }
    LaunchedEffect(uiState) {
        when (val state = uiState) {
            is AuthViewModel.LoginUIState.Error -> {
                snackbarHostState.showSnackbar(
                    message = state.message ?: "Ocorreu um erro",
                    actionLabel = "OK"
                )
                authViewModel.clearError()
            }
            else -> { /* Idle, Loading ou Success — sem Snackbar */ }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {


            Box(
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape)
                    .background(
                        Brush.linearGradient(
                            listOf(Color(0xFF1E88E5), Color(0xFF43A047))
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(45.dp)
                )
            }

            Spacer(Modifier.height(16.dp))

            Text(
                "Bem-vindo ao ExameSUS",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF222222)
            )
            Text(
                "Acesse suas informações de exames do SUS",
                fontSize = 14.sp,
                color = Color.Gray
            )

            Spacer(Modifier.height(20.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Box(modifier = Modifier
                    .weight(1f)
                    .height(40.dp)) {
                    LoginTab(
                        text = "Cidadão",
                        selected = loginType == LoginType.CIDADAO,
                        onClick = { loginType = LoginType.CIDADAO }
                    )
                }
                Box(modifier = Modifier
                    .weight(1f)
                    .height(40.dp)) {
                    LoginTab(
                        text = "Servidor",
                        selected = loginType == LoginType.SERVIDOR,
                        onClick = { loginType = LoginType.SERVIDOR }
                    )
                }
            }

            Spacer(Modifier.height(24.dp))

            when (loginType) {

                LoginType.CIDADAO -> {
                    Text("CPF ou Cartão SUS", fontWeight = FontWeight.SemiBold)
                    Spacer(Modifier.height(6.dp))

                    OutlinedTextField(
                        value = cidadaoCpf,
                        onValueChange = { cidadaoCpf = it },
                        placeholder = { Text("Digite seu CPF ou Cartão SUS") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )

                    Spacer(Modifier.height(6.dp))
                    Text(
                        "Você pode usar qualquer um dos dois documentos para acessar",
                        fontSize = 12.sp,
                        color = Color.Gray
                    )

                    Spacer(Modifier.height(20.dp))

                    LoginButton(
                        text = "Acessar Sistema",
                        isLoading = isLoading,
                        onClick = { authViewModel.loginCidadao(cidadaoCpf) }
                    )

                }

                LoginType.SERVIDOR -> {

                    Text("ID do Servidor", fontWeight = FontWeight.SemiBold)
                    Spacer(Modifier.height(6.dp))

                    OutlinedTextField(
                        value = servidorId,
                        onValueChange = { servidorId = it },
                        placeholder = { Text("Digite seu ID de servidor") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )

                    Spacer(Modifier.height(14.dp))


                    Text("Nome Completo", fontWeight = FontWeight.SemiBold)
                    Spacer(Modifier.height(6.dp))

                    OutlinedTextField(
                        value = servidorNome,
                        onValueChange = { servidorNome = it },
                        placeholder = { Text("Digite seu nome completo") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )

                    Spacer(Modifier.height(14.dp))


                    Text("Unidade de Saúde", fontWeight = FontWeight.SemiBold)
                    Spacer(Modifier.height(6.dp))

                    Box {
                        OutlinedTextField(
                            value = servidorUnidade,
                            onValueChange = {},
                            readOnly = true,
                            placeholder = { Text("Selecione sua unidade") },
                            modifier = Modifier.fillMaxWidth(),
                            trailingIcon = {
                                IconButton(onClick = { unidadeExpanded = true }) {
                                    Icon(Icons.Default.ArrowDropDown, contentDescription = null)
                                }
                            }
                        )

                        DropdownMenu(
                            expanded = unidadeExpanded,
                            onDismissRequest = { unidadeExpanded = false }
                        ) {
                            listOf("UBS Centro", "Hospital Municipal", "Policlínica Regional").forEach {
                                DropdownMenuItem(
                                    text = { Text(it) },
                                    onClick = {
                                        servidorUnidade = it
                                        unidadeExpanded = false
                                    }
                                )
                            }
                        }
                    }

                    Spacer(Modifier.height(24.dp))

                    LoginButton(
                        text = "Acessar como Servidor",
                        isLoading = isLoading,
                        onClick = { authViewModel.loginServidor(servidorId, servidorNome, servidorUnidade) }
                    )

                }
            }
        }

        // Overlay de carregamento
        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.3f)),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    color = Color.White,
                    modifier = Modifier.size(48.dp)
                )
            }
        }
    }
    }
}

@Composable
private fun LoginTab(
    text: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    val bg = if (selected) Color.White else Color(0xFFF2F2F2)
    val txt = if (selected) Color(0xFF1E88E5) else Color.Gray

    Surface(
        shape = RoundedCornerShape(10.dp),
        tonalElevation = if (selected) 2.dp else 0.dp,
        shadowElevation = if (selected) 3.dp else 0.dp,
        modifier = Modifier
            .height(40.dp)
            .padding(horizontal = 4.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(bg)
                .clickable { onClick() },
            contentAlignment = Alignment.Center
        ) {
            Text(text, color = txt, fontWeight = FontWeight.Bold)
        }
    }
}


@Composable
private fun LoginButton(
    text: String,
    isLoading: Boolean,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        enabled = !isLoading,
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
            disabledContainerColor = Color.Transparent
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.horizontalGradient(
                        listOf(
                            if (isLoading) Color(0xFF1E88E5).copy(alpha = 0.6f) else Color(0xFF1E88E5),
                            if (isLoading) Color(0xFF43A047).copy(alpha = 0.6f) else Color(0xFF43A047)
                        )
                    ),
                    RoundedCornerShape(12.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(24.dp),
                    color = Color.White,
                    strokeWidth = 2.dp
                )
            } else {
                Text(text, color = Color.White, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewLogin() {
    KoinApplication(application = { modules(appModule) }) {
        val navControllerPreview = rememberNavController()
        val authViewModelPreview: AuthViewModel = koinViewModel()
        LoginScreen(
            navController = navControllerPreview,
            authViewModel = authViewModelPreview
        )
    }
}
