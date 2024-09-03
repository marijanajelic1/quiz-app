package com.example.quizapp.ui

import androidx.compose.foundation.clickable
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
import androidx.compose.runtime.Composable
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
import com.example.quizapp.viewmodel.DeleteQuestionsViewModel

@Composable
fun DeleteQuestionsScreen(
    navController: NavController,
    viewModel: DeleteQuestionsViewModel = viewModel(factory = DeleteQuestionsViewModel.Factory)
) {
    AppBackground {
        val uiState by viewModel.uiState.collectAsState()
        val questions by viewModel.questions.collectAsState(emptyList())

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(questions) { question ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { viewModel.onDeleteQuestion(question) },
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "ID: ${question.id} - ${question.text}",
                                style = MaterialTheme.typography.bodyLarge
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
                    onClick = { navController.navigate(Screens.START.route) },
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
            }
        }

        if (uiState.showConfirmationDialog) {
            DeleteQuestionDialog(
                questionText = viewModel.questionToDelete?.text.orEmpty(),
                onConfirmDelete = {
                    viewModel.onConfirmDelete()
                    navController.navigate(Screens.START.route)
                },
                onDismiss = {
                    viewModel.onCancelDelete()
                }
            )
        }
    }
}