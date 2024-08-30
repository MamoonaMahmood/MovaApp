package com.example.myapplication.Data

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "user_likes")
data class UserData(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val posterPath: String,
    val voteAverage: Double
)
//@Entity(tableName = "user_likes_new")
//data class UserLikes(
//
//    @PrimaryKey
//    val title: String,
//    val posterPath: String,
//    val voteAverage: Double
//
//)

