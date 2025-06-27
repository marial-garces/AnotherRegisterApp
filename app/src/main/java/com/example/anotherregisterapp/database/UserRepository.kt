package com.example.anotherregisterapp.database

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.security.MessageDigest


class UserRepository(private val dao: UserDatabaseDao) {

    suspend fun register(userName: String, email: String, password: String): Long {
        return withContext(Dispatchers.IO) {
            try {
                if (!User.isValidEmail(email)){
                    throw IllegalArgumentException("Invalid email format")
                }

                if (!User.isValidUserName(userName)) {
                    throw IllegalArgumentException("Username should be at least 3 characters long and contain only letters, numbers, and underscores")
                }

                if (!User.isValidPassword(password)) {
                    throw IllegalArgumentException("Password should be at least 6 characters long")
                }

                val existingUserByEmail = dao.findByEmail(email.lowercase().trim())
                if (existingUserByEmail != null) {
                    throw IllegalArgumentException("Email is already registered")
                }

                val existingUserByName = dao.findByUserName(userName.lowercase().trim())
                if( existingUserByName != null) {
                    throw IllegalArgumentException("Username is already taken")
                }

                val hashedPassword = hashPassword(password)
                val user = User(
                    userName = userName.lowercase().trim(),
                    email = email.lowercase().trim(),
                    password = hashedPassword
                )

                val userId = dao.insert(user)
                Log.d("UserRepository", "User registered with ID: $userId")
                userId

            } catch (e: Exception){
                Log.e("UserRepository", "Error validating user data: ${e.message}")
                throw e
            }
        }
    }

    suspend fun login(email: String, password: String): User? {
        return withContext(Dispatchers.IO) {
            try{
                if(email.isBlank() || password.isBlank()){
                    return@withContext null
                }

                val user = dao.findByEmail(email.lowercase().trim())
                if(user != null && user.isActive){
                    val hashedPassword = hashPassword(password)
                    if(hashedPassword == user.password){
                        Log.d("UserRepository", "User logged in: ${user.userName}")
                        return@withContext user
                    }
                }

                Log.d("UserRepository", "Login failed for email: $email")
                null
            }catch (e: Exception){
                Log.e("UserRepository", "Error during login: ${e.message}")
                null
            }
        }
    }

    suspend fun getUserById(userId: Long): User? {
        return withContext(Dispatchers.IO) {
            try { dao.getUserById(userId) }
            catch (e: Exception) {
                Log.e("UserRepository", "Error fetching user by ID: ${e.message}")
                null
            }
        }
    }

    suspend fun updateUserStatus(userId: Long, isActive: Boolean){
        withContext(Dispatchers.IO) {
            try {
                dao.updateUserStatus(userId, isActive)
                Log.d("UserRepository", "User status updated: $userId, isActive: $isActive")
            }catch (e: Exception) {
                Log.e("UserRepository", "Error updating user status: ${e.message}")
                throw e
            }
        }
    }

    suspend fun getUserCount(): Int{
        return withContext(Dispatchers.IO) {
            dao.getUserCount()
        }
    }

    private fun hashPassword(password: String): String{
        val bytes = password.toByteArray()
        val md = MessageDigest.getInstance("SHA-256")
        val digest = md.digest(bytes)
        return digest.fold(""){ str, it -> str + "%02x".format(bytes)}
    }

    suspend fun deleteAllUsers(){
        withContext(Dispatchers.IO) {
            dao.deleteAllUsers()
        }
    }



}