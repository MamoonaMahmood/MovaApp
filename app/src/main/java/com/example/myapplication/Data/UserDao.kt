package com.example.myapplication.Data

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addLikes(userData: UserData)

    @Query("SELECT * FROM user_likes ORDER BY id ASC")
    fun readAllData(): PagingSource <Int,UserData>


}