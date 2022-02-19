package com.mywork.templateapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.mywork.templateapp.data.models.AuthUser
import com.mywork.templateapp.repository.AuthRepository
import com.mywork.templateapp.utils.network.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AuthViewModel
@Inject constructor(private val authRepository: AuthRepository) : ViewModel() {


    fun login(phone: String): LiveData<DataState<AuthUser>> {
        return authRepository.login(phone)
    }


    override fun onCleared() {
        super.onCleared()
        authRepository.cancelActiveJobs()
    }
}

