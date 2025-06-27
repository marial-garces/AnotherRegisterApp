package com.example.anotherregisterapp.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(
    tableName = "user_table",
    indices = [
        Index(value = ["email"], unique = true),
        Index(value = ["user_name"], unique = true)
    ]
)
@Parcelize
data class User(
    @PrimaryKey(autoGenerate = true) val userId: Long = 0,

    @ColumnInfo(name = "email") val email: String,

    @ColumnInfo(name = "user_name") val userName: String,

    @ColumnInfo(name = "password") val password: String,

    @ColumnInfo(name = "created_at") val createdAt: Long = System.currentTimeMillis(),

    @ColumnInfo(name = "is_active") val isActive: Boolean = true

) : Parcelable{

    companion object {
        fun isValidEmail(email: String): Boolean {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
        }

        fun isValidPassword(password: String): Boolean {
            return password.length >= 6
        }

        fun isValidUserName(userName: String): Boolean {
            return userName.length >= 3 && userName.matches(Regex("^[a-zA-Z0-9_]+$"))
        }
    }
}