package com.fhhgb.loggingapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Preview
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Sensors
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.fhhgb.loggingapp.ui.theme.LoggingAppTheme

class ShowcaseActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LoggingAppTheme {
                MainContent()
            }
        }
    }
}

@Composable
private fun MainContent() {
    val navController = rememberNavController()
    var currentRoute by remember { mutableStateOf(Overview.route) }
    Scaffold(
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = currentRoute == Overview.route,
                    onClick = {
                        currentRoute = Overview.route
                        navController.navigate(Overview.route)
                    },
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Preview,
                            contentDescription = "Overview"
                        )
                    },
                    label = { Text("Overview") },
                )
                NavigationBarItem(
                    selected = currentRoute == Person.route,
                    onClick = {
                        currentRoute = Person.route
                        navController.navigate(Person.route)
                    },
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "Overview"
                        )
                    },
                    label = { Text("Person") },
                )
                NavigationBarItem(
                    selected = currentRoute == Sensor.route,
                    onClick = {
                        currentRoute = Sensor.route
                        navController.navigate(Sensor.route)
                    },
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Sensors,
                            contentDescription = "Overview"
                        )
                    },
                    label = { Text("Sensor") },
                )
                NavigationBarItem(
                    selected = currentRoute == Intents.route,
                    onClick = {
                        currentRoute = Intents.route
                        navController.navigate(Intents.route)
                    },
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Send,
                            contentDescription = "Overview"
                        )
                    },
                    label = { Text("Intents") },
                )
            }
        },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        NavigationManager(innerPadding, navController)
    }
}

@Composable
private fun NavigationManager(
    innerPadding: PaddingValues,
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Overview.route,
        modifier = Modifier.padding(innerPadding)
    ) {
        composable(Overview.route) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(dimensionResource(R.dimen.padding_medium)),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Overview Screen")
                Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_small)))
                Text(text = "You are logged in.")
            }
        }

        composable(Person.route) {
            val viewModel: LoginViewModel = viewModel()
            val uiState by viewModel.loginState.collectAsState()
            val context = LocalContext.current
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(dimensionResource(R.dimen.padding_medium)),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Account Screen")
                Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_small)))
                Text(text = "Username: ${uiState.userName}")
                Text(text = "Logged in: ${uiState.isLoggedIn}")

                Button(
                    onClick = {
                        viewModel.onAction(LoginViewModel.LoginAction.Logout)

                        val intent = Intent(context, MainActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        context.startActivity(intent)

                        (context as Activity).finish()
                    }
                ) {
                    Text("Logout")
                }
            }
        }

        composable(Sensor.route) {
            Text("Sensor", modifier = Modifier.fillMaxSize()) //Fill the screen in here
        }
        composable(Intents.route) {
            Text("Intents", modifier = Modifier.fillMaxSize()) //Fill the screen in here
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NavigationPreview() {
    LoggingAppTheme {
        MainContent()
    }
}