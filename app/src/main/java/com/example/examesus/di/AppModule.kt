package com.example.examesus.di

import com.example.examesus.data.ExameRepository
import com.example.examesus.data.UserRepository
import com.example.examesus.data.local.AppDatabase
import com.example.examesus.data.local.ExameDao
import com.example.examesus.viewmodel.AuthViewModel
import com.example.examesus.viewmodel.ExameViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    // Room — bd local (persistencia)
    single {
        AppDatabase.create(androidContext())
    }
    single { get<AppDatabase>().exameDao() }

    // repos — singletons
    single { UserRepository() }
    single { ExameRepository(get()) }

    // viewmodels — Koin cria automaticamente e injeta as dependências
    viewModel { AuthViewModel(get()) }
    viewModel { ExameViewModel(get(), get()) }
}
