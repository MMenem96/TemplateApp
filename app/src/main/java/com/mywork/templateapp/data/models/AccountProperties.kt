package com.mywork.templateapp.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(tableName = "account_properties")
data class AccountProperties(
    @SerializedName("id")
    @Expose
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "pk")
    var pk: Int,

    @SerializedName("email")
    @Expose
    @ColumnInfo(name = "email")
    var email: String? = null,

    @SerializedName("phone")
    @Expose
    @ColumnInfo(name = "phone")
    var phone: String?,

    @SerializedName("name")
    @Expose
    @ColumnInfo(name = "name")
    var name: String?
)