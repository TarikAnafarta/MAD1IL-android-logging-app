package com.fhhgb.loggingapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.fhhgb.loggingapp.ui.theme.LoggingAppTheme
import com.fhhgb.loggingapp.ui.theme.darkGreen
import com.fhhgb.loggingapp.ui.theme.tintColor

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val viewModel: LoginViewModel by viewModels()

        setContent {
            val loginState by viewModel.loginState.collectAsState()
            MainScreen(loginState, viewModel::onAction)
        }
    }

    @Composable
    fun MainScreen(
        loginState: LoginViewModel.LoginState,
        runAction: (LoginViewModel.LoginAction) -> Unit
    ) {
        LocalContext.current
        LoggingAppTheme {
            Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                Column(
                    modifier = Modifier
                        .padding(paddingValues = innerPadding)
                        .padding(all = dimensionResource(R.dimen.padding_medium))
                        .fillMaxWidth()
                        .verticalScroll(rememberScrollState())
                ) {
                    Spacer(modifier = Modifier.height(dimensionResource(R.dimen.height_large)))
                    Image(
                        painter = painterResource(R.drawable.ic_launcher_foreground),
                        contentDescription = "Button to call the dialog",
                        colorFilter = ColorFilter.tint(Color.Blue),
                        modifier = Modifier
                            .size(dimensionResource(R.dimen.spacer_large))
                            .align(Alignment.CenterHorizontally)
                    )

                    OutlinedTextField(
                        value = loginState.userName, //Prefill the username on next startup
                        onValueChange = { input -> runAction(LoginViewModel.LoginAction.OnUserNameChanged(input)) },
                        leadingIcon = { Icon(Icons.Rounded.Person, contentDescription = "Icon") },
                        label = { Text(text = stringResource(R.string.username)) },
                        modifier = Modifier
                            .padding(vertical = dimensionResource(R.dimen.padding_medium))
                            .fillMaxWidth(),
                        isError = loginState.isError, // for the red outlines
                        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Default),
                    )
                    OutlinedTextField(
                        value = loginState.password,
                        onValueChange = { input -> runAction(LoginViewModel.LoginAction.OnUserPasswordChanged(input)) },
                        leadingIcon = { Icon(Icons.Rounded.Lock, contentDescription = "Icon") },
                        label = { Text(text = stringResource(R.string.password)) },
                        modifier = Modifier
                            .padding(vertical = dimensionResource(R.dimen.padding_medium))
                            .fillMaxWidth(),
                        isError = loginState.isError, // for the red outlines
                        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Default),
                    )

                    Button(
                        modifier = Modifier
                            .padding(vertical = dimensionResource(R.dimen.padding_medium))
                            .fillMaxWidth(),
                        onClick = { runAction(LoginViewModel.LoginAction.VerifyLoginData) }) {
                        Text(
                            text = stringResource(R.string.login),
                            fontSize = dimensionResource(R.dimen.text_large).value.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    if (loginState.showDialog) {
                        AlertDialog(
                            onDismissRequest = {},
                            icon = {
                                Icon(
                                    imageVector = Icons.Default.Info,
                                    contentDescription = "Icon",
                                    tint = tintColor
                                )
                            },
                            title = {
                                if (loginState.isLoggedIn)
                                    Text(
                                        stringResource(R.string.success),
                                        color = darkGreen
                                    )
                                else
                                    Text(
                                        stringResource(R.string.fail),
                                        color = Color.Red
                                    )
                            },
                            confirmButton = {
                                Button(
                                    onClick = { runAction(LoginViewModel.LoginAction.ResetDialog) }
                                ) {
                                    Text(stringResource(R.string.confirm))
                                }
                            },
                            dismissButton = {
                                TextButton(
                                    onClick = { runAction(LoginViewModel.LoginAction.ResetDialog) }
                                ) {
                                    Text(stringResource(R.string.dismiss))
                                }
                            }
                        )
                    }
                }
            }
        }
    }

    @Composable
    @Preview
    fun ExamplePreview() {
        MainScreen(
            LoginViewModel.LoginState(),
            {}
        )
    }
}