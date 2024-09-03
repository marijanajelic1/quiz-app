package com.example.quizapp.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.quizapp.R
import com.example.quizapp.Screens
import com.example.quizapp.viewmodel.AnswerQuestionsViewModel

@Composable
fun QuestionScreen(
    navController: NavController,
    fieldId: Int,
    modifier: Modifier = Modifier,
    viewModel: AnswerQuestionsViewModel = viewModel(factory = AnswerQuestionsViewModel.Factory)
) {
    AppBackground {
        val uiState by viewModel.uiState.collectAsState()

        LaunchedEffect(fieldId) {
            viewModel.getQuestionsByFieldId(fieldId)
        }

        Column(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LazyColumn(
                modifier = Modifier.weight(.7F),
                verticalArrangement = Arrangement.Center
            ) {
                items(uiState.questions) { question ->
                    Card(
                        modifier = Modifier
                            .width(350.dp)
                            .height(150.dp)
                            .padding(vertical = 8.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp),
                            verticalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = question.text,
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier.padding(bottom = 8.dp)
                            )

                            TextField(
                                value = uiState.answers[question.id] ?: "",
                                onValueChange = { newText ->
                                    viewModel.updateAnswer(question.id, newText)
                                },
                                placeholder = { Text(stringResource(id = R.string.type_your_answer)) },
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = {
                        navController.navigate(Screens.FIELDS.route)
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
                    Text(text = stringResource(id = R.string.back))
                }

                Button(
                    onClick = {
                        viewModel.checkAnswers()
                    },
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .width(140.dp)
                        .height(50.dp)
                ) {
                    Text(text = stringResource(id = R.string.confirm))
                }
            }

            if (uiState.showDialog) {
                FinalScoreDialog(
                    score = uiState.correctAnswersCount,
                    onPlayAgain = {
                        navController.navigate(Screens.FIELDS.route)
                    },
                    onDismiss = {
                        viewModel.dismissDialog()
                    }
                )
            }
        }
    }
}

