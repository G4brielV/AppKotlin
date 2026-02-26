package com.example.appkotlin.ui.detail

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.appkotlin.di.ViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserDetailScreen(
    viewModelFactory: ViewModelFactory,
    userId: Int,
    onBackClick: () -> Unit
) {
    val viewModel: UserDetailViewModel = viewModel(factory = viewModelFactory)
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(userId) {
        viewModel.getUser(userId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("User Details") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Box(modifier = Modifier.fillMaxSize().padding(padding)) {
            when (val state = uiState) {
                is UserDetailUiState.Loading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
                is UserDetailUiState.Error -> {
                    Text(
                        text = state.message,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                is UserDetailUiState.Success -> {
                    val user = state.user
                    Column(
                        modifier = Modifier.fillMaxSize().padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        AsyncImage(
                            model = user.image,
                            contentDescription = null,
                            modifier = Modifier.size(128.dp)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(text = "${user.firstName} ${user.lastName}", style = MaterialTheme.typography.headlineMedium)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = "@${user.username}", style = MaterialTheme.typography.bodyLarge)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = user.email, style = MaterialTheme.typography.bodyLarge)
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(text = "Age: ${user.age}")
                        Text(text = "Gender: ${user.gender}")
                        Text(text = "Birth Date: ${user.birthDate}")
                        Text(text = "Hair: ${user.hair.color}, ${user.hair.type}")
                    }
                }
            }
        }
    }
}
