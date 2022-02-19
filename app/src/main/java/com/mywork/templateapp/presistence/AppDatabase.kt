package com.mywork.templateapp.presistence

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mywork.templateapp.data.models.AuthUser
import com.mywork.templateapp.utils.Constants.Companion.DATABASE_VERSION

@Database(
    entities = [AuthUser::class],
    version = DATABASE_VERSION
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getAuthTokenDao(): AuthTokenDao
}