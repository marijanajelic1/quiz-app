package com.example.quizapp.ui

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.quizapp.R

@Composable
fun FinalScoreDialog(
    score: Int,
    onPlayAgain: () -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = if (score == 0) {
                    stringResource(id = R.string.try_again)
                } else {
                    stringResource(id = R.string.congratulations)
                }
            )
        },
        text = {
            Text(
                text = if (score == 0) {
                    stringResource(id = R.string.no_right_answers)
                } else {
                    "${stringResource(id = R.string.number_of_right_answers)}: $score"
                }
            )
        },
        modifier = modifier,
        dismissButton = {
            TextButton(
                onClick = onDismiss
            ) {
                Text(text = stringResource(id = R.string.close))
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onPlayAgain()
                }
            ) {
                Text(text = stringResource(id = R.string.new_round))
            }
        }
    )
}
