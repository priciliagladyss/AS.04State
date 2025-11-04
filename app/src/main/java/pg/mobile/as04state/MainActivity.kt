package pg.mobile.as04state

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import pg.mobile.as04state.ui.AppViewModel
import pg.mobile.as04state.ui.theme.AS04StateTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AS04StateTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { paddingValues ->
                    FormScreen(modifier = Modifier.padding(paddingValues))
                }
            }
        }
    }
}

@Composable
fun FormScreen(modifier: Modifier) {
    val vm: AppViewModel = viewModel()
    val uiState by vm.uiState.collectAsState()
    var isSubmitted by rememberSaveable { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Register", style = MaterialTheme.typography.headlineMedium)

        Spacer(Modifier.height(24.dp))


        OutlinedTextField(
            value = uiState.email,
            onValueChange = {
                vm.onEmailChange(it)
                isSubmitted = false
            },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth(),
            isError = uiState.emailError != null
        )
        if (uiState.emailError != null) {
            Text(
                text = uiState.emailError!!,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
        }

        Spacer(modifier = Modifier.height(8.dp))


        OutlinedTextField(
            value = uiState.password,
            onValueChange = {
                vm.onPasswordChange(it)
                isSubmitted = false
            },
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth(),
            isError = uiState.passwordError != null,
            visualTransformation = PasswordVisualTransformation()
        )
        if (uiState.passwordError != null) {
            Text(
                text = uiState.passwordError!!,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
        }

        Spacer(modifier = Modifier.height(8.dp))


        OutlinedTextField(
            value = uiState.firstName,
            onValueChange = {
                vm.onFirstNameChange(it)
                isSubmitted = false
            },
            label = { Text("Nama Depan") },
            modifier = Modifier.fillMaxWidth(),
            isError = uiState.firstNameError != null
        )
        if (uiState.firstNameError != null) {
            Text(
                text = uiState.firstNameError!!,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
        }

        Spacer(modifier = Modifier.height(8.dp))


        OutlinedTextField(
            value = uiState.lastName,
            onValueChange = {
                vm.onLastNameChange(it)
                isSubmitted = false
            },
            label = { Text("Nama Belakang") },
            modifier = Modifier.fillMaxWidth(),
            isError = uiState.lastNameError != null
        )
        if (uiState.lastNameError != null) {
            Text(
                text = uiState.lastNameError!!,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                vm.submit()
                isSubmitted = true
            },
            enabled = uiState.isValid
        ) {
            Text("Register")
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (isSubmitted) {
            Text("Nama lengkap: ${uiState.fullName}")
        }
    }
}
