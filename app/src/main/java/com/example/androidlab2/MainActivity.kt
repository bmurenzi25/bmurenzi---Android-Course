package com.example.androidlab2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.androidlab2.ui.theme.AndroidLab2Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidLab2Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    DrawUI()
                }
            }
        }
    }
}

@Composable
fun DrawUI()
{
    var headerText by remember { mutableStateOf(R.string.header_text) }
    var inputText by remember { mutableStateOf("") }
    var showSecondImage by remember { mutableStateOf(false) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.Center,
        modifier = Modifier.padding(16.dp)
    ) {
        Text(
            text = stringResource(id = R.string.header_text),
//            style = MaterialTheme.typography.titleLarge
        )
        Spacer(modifier = Modifier.height(48.dp))
        Image(
            painter = painterResource(if (showSecondImage) R.drawable.halloween1 else R.drawable.halloween2),
            contentDescription = "",
            modifier = Modifier
                .clip(shape = RoundedCornerShape(0.dp))
                .fillMaxWidth()
                .aspectRatio(3f / 2f)
        )
        Column(
            Modifier.padding(2.dp)
        ) {
            HeaderTextField(header = inputText, changed = { inputText = it })
            Spacer(modifier = Modifier.height(24.dp))
            UpdateUI ({ headerText })
        }
    }
}

@Composable
fun HeaderTextField(header: String, changed: (String) ->Unit){
    TextField(
        value = header,
        label = {Text(stringResource(id = R.string.enter_header_text))},
        onValueChange = changed,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp, bottom = 10.dp)
    )
}

@Composable
fun UpdateUI(clicked: () -> Unit){
    Button(onClick= clicked) {
        Text(stringResource(id = R.string.btn_updateUI))
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    AndroidLab2Theme {
        DrawUI()
    }
}