package com.mywork.templateapp.di

import android.content.SharedPreferences
import com.mywork.templateapp.data.api.ApiInterface
import com.mywork.templateapp.presistence.AuthTokenDao
import com.mywork.templateapp.repository.AuthRepository
import com.mywork.templateapp.session.SessionManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

//Think of the module as the “bag” from where we will get our dependencies from.

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    //Auth Repos
    @Singleton
    @Provides
    fun providesAuthRepository(
        authTokenDao: AuthTokenDao,
        apiInterface: ApiInterface,
        sharedPreferences: SharedPreferences,
        sessionManager: SessionManager
    ): AuthRepository {
        return AuthRepository(authTokenDao, apiInterface, sharedPreferences, sessionManager)
    }
}