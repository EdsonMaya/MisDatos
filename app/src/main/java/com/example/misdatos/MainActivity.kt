package com.example.misdatos

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.misdatos.ui.theme.MisDatosTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.example.misdatos.viewmodels.PreferencesViewModel
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val context: Context = LocalContext.current
            val preferencesViewModel =  PreferencesViewModel(context)
            MisDatosTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    SettingsView(preferencesViewModel)
                }
            }
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsView(preferencesViewModel:PreferencesViewModel) {
    var nombre by rememberSaveable {
        mutableStateOf("")
    }
    var edad by rememberSaveable {
        mutableStateOf("")
    }
    var estatura by rememberSaveable {
        mutableStateOf("")
    }
    var peso by rememberSaveable {
        mutableStateOf("")
    }
    var hobby by rememberSaveable {
        mutableStateOf("")
    }

    var corrutineScope = rememberCoroutineScope()
    var savedAge = preferencesViewModel.age.collectAsState(initial = 0)
    var savedName = preferencesViewModel.name.collectAsState(initial = "")
    var savedEstatura = preferencesViewModel.estatura.collectAsState(initial = 0)
    var savedPeso = preferencesViewModel.peso.collectAsState(initial = 0)
    var savedHobby = preferencesViewModel.hobby.collectAsState(initial = "")

    Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        if (savedName.value == "") {
            TextField(value = nombre, onValueChange = {
                nombre = it
            })
        }

        if (savedAge.value == 0) {
            TextField(value = edad, onValueChange = {
                edad = it
            })
        }

        if (savedEstatura.value == 0) {
            TextField(value = estatura, onValueChange = {
                estatura = it
            })
        }

        if (savedPeso.value == 0) {
            TextField(value = peso, onValueChange = {
                peso = it
            })
        }

        if (savedHobby.value == "") {
            TextField(value = hobby, onValueChange = {
                hobby = it
            })
        }

        if (savedName.value == "" && savedAge.value == 0 && savedEstatura.value == 0 && savedPeso.value == 0 && savedHobby.value == "") {
            Button(onClick = {
                corrutineScope.launch {
                    if (edad.toIntOrNull() != null) {
                        preferencesViewModel.setNameAndAge(
                            nombre, edad.toInt(),
                            estatura.toInt(), peso.toInt(), hobby
                        ) //copiar
                    }
                }
            }) {
                Text(text = "Guardar Datos")
            }

        }
        Text(
            text = "Tu nombre es ${savedName.value} y tienes ${savedAge.value} años," +
                    " con estatura de  ${savedEstatura.value} centimetros, un peso de ${savedPeso.value} kilos y " +
                    "tiene el hobby de ${savedHobby.value}"
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MisDatosTheme {
        val context: Context = LocalContext.current
        val preferencesViewModel =  PreferencesViewModel(context)
        SettingsView(preferencesViewModel)
    }
}