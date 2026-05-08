package com.aarav.notesapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.aarav.notesapp.screens.DisplayNotesList
import com.aarav.notesapp.screens.SettingsScreen
import com.aarav.notesapp.screens.UpdateNoteScreen
import com.aarav.notesapp.viewmodel.NoteViewModel
import com.aarav.notesapp.viewmodel.ThemeViewModel

@Composable
fun NavGraph(
    navController: NavHostController,
    viewModel: NoteViewModel,
    themeViewModel: ThemeViewModel
) {
    NavHost(navController, startDestination = "home") {
        AddHomeScreen(navController, this, viewModel)
        AddUpdateScreen(navController, this, viewModel)
        AddSettingsScreen(this, themeViewModel, viewModel, navController)
    }
}

fun AddHomeScreen(
    navController: NavHostController,
    navGraphBuilder: NavGraphBuilder,
    viewModel: NoteViewModel
) {
    navGraphBuilder.composable(route = "home") {
        DisplayNotesList(navController, viewModel)
    }
}

fun AddUpdateScreen(
    navController: NavController,
    navGraphBuilder: NavGraphBuilder,
    viewModel: NoteViewModel
) {
    navGraphBuilder.composable(
        route = "update/{id}",
        arguments = listOf(
            navArgument("id") {
                type = NavType.IntType
            }
        )
    ) { navBackStackEntry ->
        val args = navBackStackEntry.arguments
        UpdateNoteScreen(
            navigateToHome = { navController.popBackStack() },
            viewModel = viewModel,
            noteID = args?.getInt("id")!!
        )
    }
}

fun AddSettingsScreen(
    navGraphBuilder: NavGraphBuilder,
    themeViewModel: ThemeViewModel,
    viewModel: NoteViewModel,
    navController: NavController
) {
    navGraphBuilder.composable(route = "settings") {
        SettingsScreen(
            themeViewModel = themeViewModel,
            noteViewModel = viewModel,
            onBack = { navController.popBackStack() }
        )
    }
}