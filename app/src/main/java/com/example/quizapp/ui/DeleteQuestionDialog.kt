package com.example.quizapp.ui

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource

import com.example.quizapp.R

@Composable
fun DeleteQuestionDialog(
    questionText: String,
    onConfirmDelete: () -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                stringResource(id = R.string.confirm_delete)
            )
        },
        text = {
            Text(
                "${stringResource(id = R.string.are_you_sure_you_want_to_delete_question)} \"$questionText\"?"
            )
        },
        modifier = modifier,
        dismissButton = {
            TextButton(
                onClick = onDismiss
            ) {
                Text(stringResource(id = R.string.back))
            }
        },
        confirmButton = {
            TextButton(
                onClick = onConfirmDelete
            ) {
                Text(stringResource(id = R.string.delete))
            }
        }
    )
}
