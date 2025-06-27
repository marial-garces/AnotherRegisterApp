package com.example.anotherregisterapp.database.viewModels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.anotherregisterapp.database.User

object UserManager {
    private var _currentUser by mutableStateOf<User?>(null)
    val currentUser: User? get() = _currentUser

    fun setCurrentUser(user: User?) {
        _currentUser = user
    }

    fun clearCurrentUser() {
        _currentUser = null
    }

}