package com.example.quizapp

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.quizapp.ui.AddQuestionsScreen
import com.example.quizapp.ui.DeleteQuestionsScreen
import com.example.quizapp.ui.FieldsScreen
import com.example.quizapp.ui.QuestionScreen
import com.example.quizapp.ui.StartScreen
enum class Screens(val route: String) {
    START("start"),
    FIELDS("fields"),
    QUESTIONS("questions/{fieldId}"),
    ADD_QUESTION("add_question"),
    DELETE_QUESTIONS("delete_questions")
}
@Composable
fun AppNavHost(navController: NavHostController) {
    NavHost(navController, startDestination = Screens.START.route) {
        composable(route = Screens.START.route) {
            StartScreen(navController = navController)
        }
        composable(route = Screens.FIELDS.route) {
            FieldsScreen(navController = navController)
        }
        composable(route = Screens.QUESTIONS.route) { backStackEntry ->
            val fieldId = backStackEntry.arguments?.getString("fieldId")?.toIntOrNull()
            if (fieldId != null) {
                QuestionScreen(navController = navController, fieldId = fieldId)
            }
        }
        composable(route = Screens.ADD_QUESTION.route) {
            AddQuestionsScreen(navController = navController)
        }
        composable(route = Screens.DELETE_QUESTIONS.route) {
            DeleteQuestionsScreen(navController = navController)
        }
    }
}
