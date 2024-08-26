package com.example.myapplication.Data

import androidx.paging.PagingSource
import androidx.room.*

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addLikes(userData: UserData)

    @Query("SELECT * FROM user_likes ORDER BY id ASC")
    fun readAllData(): PagingSource <Int,UserData>

    @Delete
    suspend fun deleteUserLike(userData: UserData)

    @Query("DELETE FROM user_likes")
    suspend fun deleteAllUserLikes()

    @Query("SELECT COUNT (*) FROM user_likes")
    suspend fun getCount(): Int


    suspend fun checkisEmpty(): Boolean
    {
        return getCount()==0
    }


}