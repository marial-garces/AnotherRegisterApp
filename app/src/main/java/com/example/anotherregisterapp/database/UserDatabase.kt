package com.example.anotherregisterapp.database

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.AutoMigrationSpec
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities =[User::class], version = 2, exportSchema = true,
//    autoMigrations = [AutoMigration(from = 1, to = 2)]
)

abstract class UserDatabase: RoomDatabase() {
    abstract fun userDao(): UserDatabaseDao

    companion object{
        @Volatile
        private var INSTANCE: UserDatabase? = null

        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase){
                database.execSQL("CREATE UNIQUE INDEX IF NOT EXISTS `index_user_table_email` ON `user_table` (`email`)")
            }
        }

        fun getInstance(context: Context): UserDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    UserDatabase::class.java,
                    "user_database"
                )
                    .addMigrations(MIGRATION_1_2)
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance

            }
        }

        fun clearInstance() {
            INSTANCE = null
        }

    }

}