package com.example.myapplication.Data

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "user_likes")
data class UserData(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val posterPath: String,
    val voteAverage: Double
)

