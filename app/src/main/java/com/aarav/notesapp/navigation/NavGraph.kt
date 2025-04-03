package com.aarav.notesapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposableTarget
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.aarav.notesapp.roomdb.Note
import com.aarav.notesapp.screens.DisplayNotesList
import com.aarav.notesapp.screens.UpdateNoteScreen
import com.aarav.notesapp.viewmodel.NoteViewModel

@Composable
fun NavGraph(navController : NavHostController, viewModel: NoteViewModel, modifier: Modifier){
    NavHost(navController, startDestination = "home"){
        AddHomeScreen(navController, this, viewModel)
        AddUpdateScreen(navController, this, viewModel)
    }
}

fun AddHomeScreen(navController: NavHostController, navGraphBuilder: NavGraphBuilder, viewModel: NoteViewModel){
    navGraphBuilder.composable(
        route = "home"
    ) {
        DisplayNotesList(navController, viewModel)
    }
}

fun AddUpdateScreen(navController: NavController, navGraphBuilder: NavGraphBuilder, viewModel: NoteViewModel){
    navGraphBuilder.composable(
        route = "update/{id}",
        arguments = listOf(
            navArgument("id") {
                type = NavType.IntType
            }
        )
    ) {
        navBackStackEntry ->
        val args = navBackStackEntry.arguments

        UpdateNoteScreen(navigateToHome = {
            navController.popBackStack()
        },
            viewModel = viewModel,
            noteID = args?.getInt("id")!!
        )
    }
}