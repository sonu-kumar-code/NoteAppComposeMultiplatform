package org.example.project.di

import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import org.example.project.data.DatabaseFactory
import org.example.project.data.NoteDatabase
import org.example.project.data.NoteRepositoryImpl
import org.example.project.domain.AddNote
import org.example.project.domain.DeleteNote
import org.example.project.domain.GetNote
import org.example.project.domain.GetNotes
import org.example.project.domain.NoteRepository
import org.example.project.domain.NoteUseCases
import org.example.project.presentation.add_edit_note.AddEditNoteViewModel
import org.example.project.presentation.notes.NotesViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.bind
import org.koin.dsl.module

expect val platformModule: Module

val sharedModule = module {
    single {
        get<DatabaseFactory>().create()
            .setDriver(BundledSQLiteDriver())
            .build()
    }
    single { get<NoteDatabase>().noteDao }
    singleOf(::NoteRepositoryImpl).bind<NoteRepository>()
    single { GetNotes(get()) }
    single { DeleteNote(get()) }
    single { AddNote(get()) }
    single { GetNote(get()) }
    single { NoteUseCases(get(), get(), get(), get()) }
    viewModel { NotesViewModel(get()) }
    viewModel { AddEditNoteViewModel(get(), get()) }
}