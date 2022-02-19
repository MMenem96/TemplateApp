package com.mywork.templateapp.data.api

import androidx.lifecycle.LiveData
import com.mywork.templateapp.data.GenericResponse
import com.mywork.templateapp.data.models.AuthUser
import com.mywork.templateapp.utils.network.GenericApiResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiInterface {
/*   The suspend modifier is brought to us by Kotlin Coroutines, it indicates that the following function will
   execute in a coroutine (similar to a thread) allowing us to keep the UI thread unblocked while long lasting
    operations such as getting our data from the internet are being executed.*/

    @POST("login")
    @FormUrlEncoded
    fun login(@Field("phone") phone: String): LiveData<GenericApiResponse<GenericResponse<AuthUser>>>
}