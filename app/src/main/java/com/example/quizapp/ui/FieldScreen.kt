package com.example.quizapp.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.quizapp.Screens
import com.example.quizapp.viewmodel.FieldViewModel
import com.example.quizapp.R

@Composable
fun FieldsScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: FieldViewModel = viewModel(factory = FieldViewModel.Factory)
) {
    AppBackground {
        val uiState by viewModel.uiState.collectAsState()

        Box(modifier = modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 30.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(id = R.string.fields),
                    style = MaterialTheme.typography.displayLarge.copy(
                        fontFamily = FontFamily.Serif,
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier.padding(vertical = 10.dp)
                )
                LazyColumn(
                    modifier = Modifier.weight(.7F),
                    verticalArrangement = Arrangement.Center
                ) {
                    items(uiState.fieldList) { field ->
                        Card(
                            modifier = Modifier
                                .width(200.dp)
                                .height(80.dp)
                                .padding(vertical = 8.dp)
                                .clickable {
                                    navController.navigate("questions/${field.id}")
                                }
                        ) {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = field.name,
                                    style = MaterialTheme.typography.displaySmall
                                )
                            }
                        }
                    }
                }
            }
            Button(
                onClick = { navController.navigate(Screens.START.route) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = Color.Black
                ),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(start = 16.dp, bottom = 16.dp)
                    .size(130.dp, 55.dp)
            ) {
                Text(stringResource(id = R.string.back))
            }
        }
    }
}
