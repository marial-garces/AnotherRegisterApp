package com.example.anotherregisterapp.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "user_table")
@Parcelize
data class User(
    @PrimaryKey(autoGenerate = true) val userId: Long = 0,

    @ColumnInfo(name = "email", index = true) val email: String,

    @ColumnInfo(name = "user_name") val userName: String,

    @ColumnInfo(name = "password") val password: String

) : Parcelable