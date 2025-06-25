package com.example.anotherregisterapp.database

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities =[User::class], version = 1, exportSchema = true,
//    autoMigrations = [AutoMigration(from = 1, to = 2)]
)

abstract class UserDatabase: RoomDatabase() {
    abstract fun userDao(): UserDatabaseDao

    companion object{
        @Volatile
        private var INSTANCE: UserDatabase? = null

        fun getInstance(context: Context): UserDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    UserDatabase::class.java,
                    "user_database"
                ).build().also {INSTANCE = it}

            }
    }

}