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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.examesus.viewmodel.AuthViewModel


enum class LoginType { CIDADAO, SERVIDOR }

@Composable
fun LoginScreen(
    navController: NavController,
    authViewModel: AuthViewModel
    ) {
    var loginType by remember { mutableStateOf(LoginType.CIDADAO) }

    // Cidadão
    var cidadaoCpf by remember { mutableStateOf("") }

    // Servidor
    var servidorId by remember { mutableStateOf("") }
    var servidorNome by remember { mutableStateOf("") }
    var servidorUnidade by remember { mutableStateOf("") }
    var unidadeExpanded by remember { mutableStateOf(false) }

    LaunchedEffect(authViewModel.loggedUser) {
        if (authViewModel.loggedUser != null) {
            navController.navigate("dashboard")
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
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

                    LoginButton("Acessar Sistema") {
                        authViewModel.loginCidadao(cidadaoCpf)
                    }

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

                    LoginButton("Acessar como Servidor") {
                        authViewModel.loginServidor(servidorId, servidorNome, servidorUnidade)
                    }

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
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.horizontalGradient(
                        listOf(Color(0xFF1E88E5), Color(0xFF43A047))
                    ),
                    RoundedCornerShape(12.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(text, color = Color.White, fontWeight = FontWeight.Bold)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewLogin() {
    val navControllerPreview = rememberNavController()
    val authViewModelPreview: AuthViewModel = viewModel()
    LoginScreen(
        navController = navControllerPreview,
        authViewModel = authViewModelPreview
    )
}
