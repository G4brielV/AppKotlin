package com.example.appkotlin.ui.userlist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.appkotlin.di.ViewModelFactory
import com.example.appkotlin.domain.model.UserSummary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserListScreen(
    viewModelFactory: ViewModelFactory,
    onUserClick: (Int) -> Unit,
    onLogoutClick: () -> Unit
) {
    val viewModel: UserListViewModel = viewModel(factory = viewModelFactory)
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Users") },
                actions = {
                    IconButton(onClick = onLogoutClick) {
                        Icon(Icons.Default.ExitToApp, contentDescription = "Logout")
                    }
                }
            )
        }
    ) { padding ->
        Box(modifier = Modifier.fillMaxSize().padding(padding)) {
            when (val state = uiState) {
                is UserListUiState.Loading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
                is UserListUiState.Error -> {
                    Text(
                        text = state.message,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                is UserListUiState.Success -> {
                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                        items(state.users) { user ->
                            UserItem(user = user, onClick = { onUserClick(user.id) })
                        }
                        item {
                            Button(
                                onClick = { viewModel.getUsers() },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp)
                            ) {
                                Text("Load More")
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun UserItem(user: UserSummary, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = user.image,
                contentDescription = null,
                modifier = Modifier.size(64.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(text = "${user.firstName} ${user.lastName}", style = MaterialTheme.typography.bodyLarge)
            }
        }
    }
}
