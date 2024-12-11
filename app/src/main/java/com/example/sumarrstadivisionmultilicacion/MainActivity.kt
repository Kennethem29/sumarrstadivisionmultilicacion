package com.example.sumarrstadivisionmultilicacion

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.sumarrstadivisionmultilicacion.ui.theme.SumarrstadivisionmultilicacionTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SumarrstadivisionmultilicacionTheme {
                Calculator()
            }
        }
    }
}

@Composable
fun Calculator() {
    var input by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }
    var operator by remember { mutableStateOf("") }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = Color.White
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.End
        ) {
            // Display the result
            Text(
                text = if (result.isNotEmpty()) result else input,
                style = MaterialTheme.typography.headlineLarge,
                textAlign = TextAlign.End,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .background(Color.Gray.copy(alpha = 0.2f), shape = RoundedCornerShape(8.dp))
                    .padding(16.dp)
            )

            // Buttons for the calculator
            Column {
                val buttonLabels = listOf(
                    listOf("7", "8", "9", "÷"),
                    listOf("4", "5", "6", "×"),
                    listOf("1", "2", "3", "-"),
                    listOf("C", "0", "=", "+")
                )

                buttonLabels.forEach { row ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        row.forEach { label ->
                            Button(
                                onClick = {
                                    when (label) {
                                        "C" -> {
                                            input = ""
                                            result = ""
                                            operator = ""
                                        }
                                        "=" -> {
                                            if (input.isNotEmpty() && operator.isNotEmpty()) {
                                                result = calculateResult(input, operator)
                                                input = ""
                                                operator = ""
                                            }
                                        }
                                        else -> {
                                            if (label in listOf("+", "-", "×", "÷")) {
                                                if (input.isNotEmpty()) {
                                                    operator = label
                                                    input += " $label "
                                                }
                                            } else {
                                                input += label
                                            }
                                        }
                                    }
                                },
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(4.dp)
                            ) {
                                Text(text = label)
                            }
                        }
                    }
                }
            }
        }
    }
}

fun calculateResult(input: String, operator: String): String {
    val numbers = input.split(" $operator ")
    return if (numbers.size == 2) {
        val operand1 = numbers[0].toDoubleOrNull()
        val operand2 = numbers[1].toDoubleOrNull()

        if (operand1 != null && operand2 != null) {
            when (operator) {
                "+" -> (operand1 + operand2).toString()
                "-" -> (operand1 - operand2).toString()
                "×" -> (operand1 * operand2).toString()
                "÷" -> if (operand2 != 0.0) (operand1 / operand2).toString() else "Error"
                else -> "Error"
            }
        } else {
            "Error"
        }
    } else {
        "Error"
    }
}