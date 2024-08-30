package com.example.myapplication.Data

import android.content.Context
import androidx.room.*
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.internal.synchronized

@Database(entities = [UserData::class], version = 1, exportSchema = false)
abstract class UserDataBase :RoomDatabase()
{
    abstract fun userDao():UserDao

    companion object{
        @Volatile
        var INSTANCE: UserDataBase? = null

        @OptIn(InternalCoroutinesApi::class)
        fun getDataBase(context: Context): UserDataBase
        {
            val tempInstance = INSTANCE
            if(tempInstance!=null) {
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    UserDataBase::class.java,
                    "user_likes"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}
val MIGRATION_1_2 = object : Migration(1,2) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("""CREATE TABLE user_likes_new(
            title TEXT PRIMARY KEY,
            posterPath TEXT,
            voteAverage REAL
            )""")

        db.execSQL("""
            INSERT INTO user_likes_new(title, posterPath, voteAverage)
            SELECT CAST (id AS TEXT) AS title, posterPath, voteAverage 
            FROM user_likes
        """)

        db.execSQL("DROP TABLE user_likes")

        db.execSQL("ALTER TABLE user_likes_new RENAME TO user_likes")
    }
    
}