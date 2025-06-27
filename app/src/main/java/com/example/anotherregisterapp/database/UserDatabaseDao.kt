package com.example.anotherregisterapp.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface UserDatabaseDao {
    @Insert suspend fun insert(user: User): Long
    @Update suspend fun update(user: User)
    @Delete suspend fun delete(user: User)

    @Query("SELECT * FROM user_table WHERE email = :email AND password = :password LIMIT 1")
    suspend fun login(email: String, password: String): User?

    @Query("SELECT * FROM user_table WHERE email = :email LIMIT 1")
    suspend fun findByEmail(email: String): User?

    @Query("SELECT * FROM user_table WHERE userId = :userId LIMIT 1")
    suspend fun getUserById(userId: Long): User?

    @Query("SELECT * FROM user_table")
    fun getAllUsers(): LiveData<List<User>>
}