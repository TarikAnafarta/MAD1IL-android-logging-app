package com.fhhgb.loggingapp

import android.content.Intent
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
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
        val context = LocalContext.current

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
                        value = loginState.userName,
                        onValueChange = { input ->
                            runAction(LoginViewModel.LoginAction.OnUserNameChanged(input))
                        },
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
                        onValueChange = { input ->
                            runAction(LoginViewModel.LoginAction.OnUserPasswordChanged(input))
                        },
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
                        onClick = {
                            runAction(LoginViewModel.LoginAction.VerifyLoginData)
                            if (loginState.isLoggedIn) {
                                context.startActivity(
                                    Intent(context, ShowcaseActivity::class.java)
                                )
                            }
                        }
                    ) {
                        Text(
                            text = stringResource(R.string.login),
                            fontSize = dimensionResource(R.dimen.text_large).value.sp,
                            fontWeight = FontWeight.Bold
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
