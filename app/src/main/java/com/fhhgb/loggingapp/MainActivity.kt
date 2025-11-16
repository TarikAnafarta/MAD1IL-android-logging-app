package com.fhhgb.loggingapp

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.fhhgb.loggingapp.ui.theme.LoggingAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            MainScreen()
        }

    }

    @Composable
    fun MainScreen() {
        val context = LocalContext.current
        LoggingAppTheme {
            Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                Column(
                    modifier = Modifier
                        .padding(paddingValues = innerPadding)
                        .padding(all = dimensionResource(R.dimen.padding_medium))
                        .fillMaxWidth()
                        .verticalScroll(rememberScrollState()) // to allow scrolling when content doesn't fit on screen (landscape mode)
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

                    var username by rememberSaveable { mutableStateOf("") } // to keep username when phone rotates
                    OutlinedTextField(
                        value = username,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                        onValueChange = { input -> username = input },
                        leadingIcon = { Icon(Icons.Rounded.Person, contentDescription = "Icon") },
                        label = { Text(text = stringResource(R.string.username)) },
                        modifier = Modifier
                            .padding(vertical = dimensionResource(R.dimen.padding_medium))
                            .fillMaxWidth()
                        //placeholder = {Text(text = "Placeholder")}
                    )

                    var password by rememberSaveable { mutableStateOf("") } // to keep password when phone rotates
                    OutlinedTextField(
                        value = password,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                        onValueChange = { input -> password = input },
                        leadingIcon = { Icon(Icons.Rounded.Lock, contentDescription = "Icon") },
                        label = { Text(text = stringResource(R.string.password)) },
                        modifier = Modifier
                            .padding(vertical = dimensionResource(R.dimen.padding_medium))
                            .fillMaxWidth()
                    )

                    Button(
                        modifier = Modifier
                            .padding(vertical = dimensionResource(R.dimen.padding_medium))
                            .fillMaxWidth(),
                        onClick = { checkCredentials(context, username, password) }) {
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

    fun checkCredentials(context: Context, username: String, password: String) {
        if (username == "admin" && password == "password123")
            Toast.makeText(context, "Logged in", Toast.LENGTH_SHORT).show()
        else
            Toast.makeText(context, "Wrong credentials", Toast.LENGTH_SHORT).show()
    }

    @Composable
    @Preview
    fun ExamplePreview() {
        MainScreen()
    }
}