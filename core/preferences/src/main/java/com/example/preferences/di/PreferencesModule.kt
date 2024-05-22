package com.example.preferences.di

import android.content.Context
import com.example.preferences.AppPreferenceManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object PreferencesModule {
    @Provides
    @Singleton
    fun provideAppPreferenceManager(@ApplicationContext context: Context): AppPreferenceManager =
        AppPreferenceManager(context)
}