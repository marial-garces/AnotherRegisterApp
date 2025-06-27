package com.example.anotherregisterapp.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface UserDatabaseDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(user: User): Long

    @Update suspend fun update(user: User)

    @Delete suspend fun delete(user: User)

    @Query("SELECT * FROM user_table WHERE email = :email LIMIT 1")
    suspend fun findByEmail(email: String): User?

    @Query("SELECT * FROM user_table WHERE user_name = :userName LIMIT 1")
    suspend fun findByUserName(userName: String): User?

    @Query("SELECT * FROM user_table WHERE userId = :userId LIMIT 1")
    suspend fun getUserById(userId: Long): User?

    @Query("SELECT * FROM user_table WHERE is_active = 1")
    fun getAllActiveUsers(): LiveData<List<User>>

    @Query("SELECT * FROM user_table")
    fun getAllUsers(): LiveData<List<User>>

    @Query("SELECT COUNT(*) FROM user_table")
    suspend fun getUserCount(): Int

    @Query("UPDATE user_table SET is_active = :isActive WHERE userId = :userId")
    suspend fun updateUserStatus(userId: Long, isActive: Boolean)

    @Query("DELETE FROM user_table")
    suspend fun deleteAllUsers()




    //    @Query("SELECT * FROM user_table WHERE email = :email AND password = :password LIMIT 1")
//    suspend fun login(email: String, password: String): User?
}