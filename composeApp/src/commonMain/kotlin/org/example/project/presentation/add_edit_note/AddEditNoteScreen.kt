package org.example.project.presentation.add_edit_note

import androidx.compose.animation.Animatable
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import noteappcomposemultiplatform.composeapp.generated.resources.Res
import noteappcomposemultiplatform.composeapp.generated.resources.enter_des
import noteappcomposemultiplatform.composeapp.generated.resources.enter_note
import noteappcomposemultiplatform.composeapp.generated.resources.save_note
import org.example.project.black
import org.example.project.data.Note
import org.example.project.presentation.add_edit_note.components.TransparentHintTextField
import org.example.project.white
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun AddEditNoteScreen(
    navController: NavController,
    viewModel: AddEditNoteViewModel = koinViewModel()
) {
    val noteState = viewModel.noteState.collectAsState()
    val scaffoldState = remember {
        SnackbarHostState()
    }

    val noteBackgroundAnimaTable = remember {
        Animatable(
            noteState.value.color
        )
    }
    val scope = rememberCoroutineScope()

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is AddEditNoteViewModel.UiEvent.ShowSnackbar -> {
                    scaffoldState.showSnackbar(
                        message = event.message
                    )
                }

                is AddEditNoteViewModel.UiEvent.SaveNote -> {
                    navController.navigateUp()
                }
            }
        }
    }

    Scaffold(floatingActionButton = {
        FloatingActionButton(
            onClick = {
                viewModel.onEvent(AddEditNoteEvent.SaveNote)
            },
            contentColor = noteState.value.color
        ) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = stringResource(Res.string.save_note)
            )
        }
    }, snackbarHost = { SnackbarHost(hostState = scaffoldState) }) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    color = noteState.value.color
                )
                .padding(16.dp)
        ) {
            ColorRow(
                viewModel = viewModel,
                noteState = noteState.value.color,
                scope = scope,
                noteBackgroundAnimatable = noteBackgroundAnimaTable
            )
            Spacer(modifier = Modifier.height(16.dp))
            TransparentHintTextField(
                text = noteState.value.titleText,
                hint = stringResource(Res.string.enter_note),
                onValueChange = {
                    viewModel.onEvent(AddEditNoteEvent.EnteredTitle(it))
                },
                singleLine = true,
            )
            Spacer(modifier = Modifier.height(16.dp))
            TransparentHintTextField(
                text = noteState.value.contentText,
                hint = stringResource(Res.string.enter_des),
                onValueChange = {
                    viewModel.onEvent(AddEditNoteEvent.EnteredContent(it))
                },
                modifier = Modifier
                    .padding(bottom = 100.dp)
                    .weight(1f)
            )
        }
    }
}

@Composable
fun ColorRow(
    modifier: Modifier = Modifier,
    viewModel: AddEditNoteViewModel = koinViewModel(),
    noteState: Color,
    scope: CoroutineScope,
    noteBackgroundAnimatable: Animatable<Color, *>
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
            .horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Note.colors.forEach { color ->
            val colorInt = color.backgroundColor
            Box(modifier = Modifier
                    .size(50.dp)
                    .shadow(15.dp, CircleShape)
                    .clip(CircleShape)
                    .background(color = colorInt)
                    .border(
                        width = 3.dp,
                        color = if (noteState == colorInt) white else black, shape = CircleShape
                    )
                    .clickable {
                        scope.launch {
                            noteBackgroundAnimatable.animateTo(
                                targetValue = noteState, animationSpec = tween(
                                    durationMillis = 500
                                )
                            )
                        }
                        viewModel.onEvent(AddEditNoteEvent.ChangeColor(color))
                    })
        }
    }
}