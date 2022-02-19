package com.mywork.templateapp.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(
    tableName = "auth_user"
)
data class AuthUser(
    @PrimaryKey
    @ColumnInfo(name = "id")
    var id: Int? = -1,
    @SerializedName("token")
    @Expose
    @ColumnInfo(name = "token")
    var token: String? = null
) {
}