package com.mywork.templateapp.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class GenericResponse<T>(
    @SerializedName("statusCode")
    @Expose
    var statusCode: Int,
    @SerializedName("msg")
    @Expose
    var msg: String,
    @SerializedName("data")
    @Expose
    var data: T?
)