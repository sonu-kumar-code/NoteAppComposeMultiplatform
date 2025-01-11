package org.example.project

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.example.project.presentation.add_edit_note.AddEditNoteScreen
import org.example.project.presentation.add_edit_note.AddEditNoteViewModel
import org.example.project.presentation.notes.NotesScreen
import org.example.project.presentation.notes.NotesViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalAnimationApi::class)
@Composable
@Preview
fun App() {
    MaterialTheme {
        val navController = rememberNavController()
        NavHost(
            navController = navController, startDestination = Route.NoteScreenRoute
        ) {
            composable<Route.NoteScreenRoute> {
                val myViewModel: NotesViewModel = koinViewModel()
                NotesScreen(
                    navController = navController,
                    viewModel = myViewModel
                )
            }
            composable<Route.AddNoteScreenRoute> {
                val myViewModel: AddEditNoteViewModel = koinViewModel()
                AddEditNoteScreen(
                    navController = navController,
                    viewModel = myViewModel
                )
            }
        }
    }
}