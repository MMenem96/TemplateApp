package com.mywork.templateapp.presistence

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.mywork.templateapp.data.models.AuthUser

@Dao
interface AuthTokenDao {

    @Insert(onConflict = REPLACE)
    fun insertToken(authToken: AuthUser): Long

    @Query("UPDATE auth_user SET token = null WHERE  id= :pk ")
    fun removeToken(pk: Int): Int

    @Query("SELECT * FROM auth_user  WHERE  id= :pk ")
    suspend fun searchByPk(pk: Int): AuthUser?

}