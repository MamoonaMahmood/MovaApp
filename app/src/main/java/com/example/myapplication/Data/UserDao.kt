package com.example.myapplication.Data

import androidx.paging.PagingSource
import androidx.room.*

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addLikes(userData: UserData)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addLikesNew(userData: UserData)

    @Query("SELECT * FROM user_likes ORDER BY id ASC")
    fun readAllData(): PagingSource <Int,UserData>

//    @Query("SELECT * FROM user_likes_new ORDER BY title ASC")
//    fun readAllDataNew(): PagingSource <Int,UserLikes>

    @Delete
    suspend fun deleteUserLike(userData: UserData)

//    @Delete
//    suspend fun deleteUserLikeNew(userData: UserLikes)

    @Query("DELETE FROM user_likes")
    suspend fun deleteAllUserLikes()

//    @Query("DELETE FROM user_likes_new")
//    suspend fun deleteAllUserLikesNew()

    @Query("SELECT COUNT (*) FROM user_likes")
    suspend fun getCount(): Int


    suspend fun checkisEmpty(): Boolean
    {
        return getCount()==0
    }


}