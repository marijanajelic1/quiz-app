package com.example.quizapp.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.quizapp.R
import com.example.quizapp.Screens
import com.example.quizapp.entity.Field
import com.example.quizapp.viewmodel.AddQuestionsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddQuestionsScreen(
    navController: NavController,
    viewModel: AddQuestionsViewModel = viewModel(factory = AddQuestionsViewModel.Factory)
) {
    val uiState by viewModel.uiState.collectAsState()
    val fields by viewModel.fields.collectAsState(emptyList())

    var selectedField by remember { mutableStateOf<Field?>(null) }
    var questionText by remember { mutableStateOf("") }
    var answerText by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }

    AppBackground {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(id = R.string.add_question),
                style = MaterialTheme.typography.displayLarge.copy(
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier.padding(bottom = 16.dp)
            )

            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded }
            ) {
                TextField(
                    readOnly = true,
                    value = selectedField?.name ?: stringResource(id = R.string.choose_field),
                    onValueChange = {},
                    label = { Text(stringResource(id = R.string.field)) },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor()
                )

                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    fields.forEach { field ->
                        DropdownMenuItem(
                            text = { Text(text = field.name) },
                            onClick = {
                                selectedField = field
                                expanded = false
                            }
                        )
                    }
                }
            }


            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                value = questionText,
                onValueChange = { questionText = it },
                label = { Text(stringResource(id = R.string.question_text)) },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                value = answerText,
                onValueChange = { answerText = it },
                label = { Text(stringResource(id = R.string.answer)) },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = {
                        expanded = false
                        navController.navigate(Screens.START.route)
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White,
                        contentColor = Color.Black
                    ),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .width(140.dp)
                        .height(50.dp)
                ) {
                    Text(stringResource(id = R.string.back))
                }

                Button(
                    onClick = {
                        viewModel.onAddQuestion(selectedField, questionText, answerText)
                    },
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .width(140.dp)
                        .height(50.dp)
                ) {
                    Text(stringResource(id = R.string.add))
                }
            }
            Spacer(modifier = Modifier.height(16.dp))

            uiState.message?.let { message ->
                Text(
                    text = message,
                    color = if (uiState.isSuccess) Color.Green else Color.Red,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(top = 16.dp)
                )
            }
        }
    }
}

