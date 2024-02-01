package com.example.datastore

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.datastore.data.UserStore
import com.example.datastore.ui.theme.DataStoreTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalComposeUiApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DataStoreTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Main()
                }
            }
        }
    }
}

@Preview(showBackground = true)
@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun Main() {
    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val tokenValue = remember {
        mutableStateOf(TextFieldValue())
    }
    val tokenValue1 = remember {
        mutableStateOf(TextFieldValue())
    }
    val tokenValue2 = remember {
        mutableStateOf(TextFieldValue())
    }
    val store = UserStore(context)
    val tokenText = store.getAccessToken.collectAsState(initial = "")
    val tokenText1 = store.getAccessToken1.collectAsState(initial = "")
    val tokenText2 = store.getAccessToken2.collectAsState(initial = "")

    Column(
        modifier = Modifier.clickable { keyboardController?.hide() },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(30.dp))

        Text(text = "Android Lab 3", fontWeight = FontWeight.Bold, fontSize = 30.sp)

        Spacer(modifier = Modifier.height(15.dp))

        Text(text = tokenText.value)

        Spacer(modifier = Modifier.height(15.dp))

        Text(text = tokenText1.value)

        Spacer(modifier = Modifier.height(15.dp))

        Text(text = tokenText2.value)

        Spacer(modifier = Modifier.height(15.dp))

        TextField(
            value = tokenValue.value,
            onValueChange = { tokenValue.value = it },
        )

        Spacer(modifier = Modifier.height(15.dp))

        TextField(
            value = tokenValue1.value,
            onValueChange = { tokenValue1.value = it },
        )

        Spacer(modifier = Modifier.height(15.dp))

        TextField(
            value = tokenValue2.value,
            onValueChange = { tokenValue2.value = it },
        )

        Spacer(modifier = Modifier.height(30.dp))


        Row {

            Button(
                onClick = {
                    // Call the function to load stored values
                    CoroutineScope(Dispatchers.IO).launch {
                        val loadedUserName = store.loadUserName()
                        val loadedEmail = store.loadEmail()
                        val loadedStudentID = store.loadStudentID()

                        // Update the mutable state variables
                        tokenValue2.value = TextFieldValue(loadedUserName)
                        tokenValue1.value = TextFieldValue(loadedEmail)
                        tokenValue.value = TextFieldValue(loadedStudentID)
                    }
                }
            ) {
                Text(text = "Load")
            }

            Spacer(modifier = Modifier.padding(10.dp))

            Button(
                onClick = {
                    CoroutineScope(Dispatchers.IO).launch {
                        store.saveToken(tokenValue.value.text)
                        store.saveToken1(tokenValue1.value.text)
                        store.saveToken2(tokenValue2.value.text)
                    }
                }
            ) {
                Text(text = "Save")
            }
            Spacer(modifier = Modifier.padding(10.dp))

            Button(
                onClick = {
                    // Call the function to clear stored values
                    CoroutineScope(Dispatchers.IO).launch {
                        store.clearStoredValues()
                    }

                    // Clear text fields
                    tokenValue.value = TextFieldValue()
                    tokenValue1.value = TextFieldValue()
                    tokenValue2.value = TextFieldValue()
                }
            ) {
                Text(text = "Clear")
            }
        }


        Spacer(modifier = Modifier.height(50.dp))

        Text(text = "Jeet Panchal", fontWeight = FontWeight.Bold, fontSize = 20.sp)
        Text(text = "Centennial College", fontWeight = FontWeight.Bold, fontSize = 18.sp)
    }
}