package org.example.project.presentation.notes

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import noteappcomposemultiplatform.composeapp.generated.resources.Res
import noteappcomposemultiplatform.composeapp.generated.resources.add_note
import noteappcomposemultiplatform.composeapp.generated.resources.search_here
import noteappcomposemultiplatform.composeapp.generated.resources.sort
import org.example.project.Route
import org.example.project.presentation.add_edit_note.components.TransparentHintTextField
import org.example.project.presentation.notes.components.NoteItem
import org.example.project.presentation.notes.components.OrderSection
import org.example.project.red
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@ExperimentalAnimationApi
@Composable
fun NotesScreen(
    navController: NavController,
    viewModel: NotesViewModel = koinViewModel()
) {
    val state = viewModel.state.collectAsState()
    val scaffoldState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(floatingActionButton = {
        FloatingActionButton(
            onClick = {
                navController.navigate(Route.AddNoteScreenRoute(noteId = null))
            },
            contentColor = red
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = stringResource(Res.string.add_note)
            )
        }
    }, snackbarHost = { SnackbarHostState() }) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 16.dp, end = 16.dp, top = 50.dp, bottom = 16.dp)
        ) {
            TransparentHintTextField(text = state.value.searchText,
                hint = stringResource(Res.string.search_here),
                onValueChange = { viewModel.onEvent(NotesEvent.SearchItem(it)) },
                trailing = {
                    IconButton(
                        onClick = {
                            viewModel.onEvent(NotesEvent.ToggleOrderSection)
                        },
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Menu,
                            contentDescription = stringResource(Res.string.sort)
                        )
                    }
                })
            AnimatedVisibility(
                visible = state.value.isOrderSectionVisible,
                enter = fadeIn() + slideInVertically(),
                exit = fadeOut() + slideOutVertically()
            ) {
                OrderSection(modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                    noteOrder = state.value.noteOrder,
                    onOrderChange = {
                        viewModel.onEvent(NotesEvent.Order(it))
                    })
            }
            Spacer(modifier = Modifier.height(16.dp))
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(state.value.notes) { note ->
                    NoteItem(note = note, modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            navController.navigate(
                                Route.AddNoteScreenRoute(note.id)
                            )
                        }, onDeleteClick = {
                        viewModel.onEvent(NotesEvent.DeleteNote(note))
                        scope.launch {
                            val result = scaffoldState.showSnackbar(
                                message = "Note deleted", actionLabel = "Undo"
                            )
                            if (result == SnackbarResult.ActionPerformed) {
                                viewModel.onEvent(NotesEvent.RestoreNote)
                            }
                        }
                    })
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}