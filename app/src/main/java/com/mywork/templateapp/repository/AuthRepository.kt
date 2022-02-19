package com.mywork.templateapp.repository

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.LiveData
import com.mywork.templateapp.data.GenericResponse
import com.mywork.templateapp.data.api.ApiInterface
import com.mywork.templateapp.data.models.AuthUser
import com.mywork.templateapp.presistence.AuthTokenDao
import com.mywork.templateapp.session.SessionManager
import com.mywork.templateapp.utils.Response
import com.mywork.templateapp.utils.ResponseType
import com.mywork.templateapp.utils.network.*
import kotlinx.coroutines.Job
import javax.inject.Inject

class AuthRepository
@Inject constructor(
    val authTokenDao: AuthTokenDao,
    val authApiService: ApiInterface,
    val sharedPreferences: SharedPreferences,
    val sessionManager: SessionManager
) : JobManager("AuthRepository") {
    private val TAG = "AuthRepository"


    fun login(phone: String): LiveData<DataState<AuthUser>> {
//        val loginFieldErrors = LoginFields(phone).isValidForLogin()
//
//        if (!loginFieldErrors.equals(LoginFields.LoginError.none())) {
//            return returnErrorResponse(loginFieldErrors, ResponseType.Dialog())
//        }

        return object : NetworkBoundResource<GenericResponse<AuthUser>, AuthUser>(
            isNetworkAvailable = sessionManager.isInternetAvailable(),
            isNetworkRequest = true,
            shouldLoadFromCache = false,
            shouldCancelIfNoInternet = true
        ) {
            override suspend fun handleApiSuccessResponse(response: ApiSuccessResponse<GenericResponse<AuthUser>>) {
                Log.d(TAG, "Login handleApiSuccessResponse: $response")
                onCompleteJob(
                    DataState.success(
                        data = null,
                        response = Response(
                            message = "Logged In",
                            responseType = ResponseType.None()
                        )
                    )
                )
            }

            override fun createCall(): LiveData<GenericApiResponse<GenericResponse<AuthUser>>> {
                return authApiService.login(phone)
            }

            override fun setJob(job: Job) {
                addJop("login", job)
            }

            override suspend fun createCacheRequest() {
                //not used in this case
            }

            override fun loadFromCache(): LiveData<AuthUser> {
                //not used in this case
                return AbsentLiveData.create()
            }

            override suspend fun updateLocalDb(cacheObject: AuthUser?) {
                //not used in this case
            }


        }.asLiveData()
    }

}