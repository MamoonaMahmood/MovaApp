package com.example.myapplication.Repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.myapplication.Data.UserDao
import com.example.myapplication.Data.UserData
import kotlinx.coroutines.flow.Flow

class DataBaseRepo(private val userDao: UserDao) {

    fun readPagedData(): Flow<PagingData<UserData>>
    {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { userDao.readAllData() }
        ).flow
    }

//    fun readPagedDataNew(): Flow<PagingData<UserLikes>>
//    {
//        return Pager(
//            config = PagingConfig(
//                pageSize = 20,
//                enablePlaceholders = false
//            ),
//            pagingSourceFactory = { userDao.readAllDataNew() }
//        ).flow
//    }

    suspend fun addUserLike( userData: UserData) = userDao.addLikes(userData)

//    suspend fun addUserLikeNew( userData: UserLikes) = userDao.addLikesNew(userData)

    suspend fun deleteUser(userData: UserData) = userDao.deleteUserLike(userData)

//    suspend fun deleteUserNew(userData: UserLikes) = userDao.deleteUserLikeNew(userData)

    suspend fun deleteAllUsers() = userDao.deleteAllUserLikes()

//    suspend fun deleteAllUsersNew() = userDao.deleteAllUserLikesNew()

    suspend fun checkIfTableisEmpty() = userDao.checkisEmpty()
}