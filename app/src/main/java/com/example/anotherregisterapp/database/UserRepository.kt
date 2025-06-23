package com.example.anotherregisterapp.database


class UserRepository(private val dao: UserDatabaseDao) {
    suspend fun register(userName: String, email: String, password: String): Long {
        if(dao.findByEmail(email) != null) {
            throw IllegalArgumentException("Email already exists")
        }
        return dao.insert(User(userName = userName, email = email, password = password))
    }

    suspend fun login(email: String, password: String): User? {
        val user = dao.findByEmail(email) ?: return null
        return if(password == user.password) {
            user
        } else {
            null
        }
    }

    suspend fun getUserById(userId: Long): User? = dao.getUserById(userId)


    suspend fun update(user: User) = dao.update(user)
    suspend fun delete(user: User) = dao.delete(user)
}