package com.example.anotherregisterapp.database.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.anotherregisterapp.database.User
import com.example.anotherregisterapp.database.UserRepository
import kotlinx.coroutines.launch

class AuthViewModel (private val repo: UserRepository): ViewModel(){
    private val _loginResult = MutableLiveData<Result<User>>()
    val loginResult: LiveData<Result<User>> = _loginResult

    private val _registerResult = MutableLiveData<Result<Long>>()
    val registerResult: LiveData<Result<Long>> = _registerResult

    private val _currentUser  = MutableLiveData<User?>()
    val currentUser: LiveData<User?> = _currentUser

    fun login(email: String, password:String) = viewModelScope.launch {
        val user = repo.login(email, password)
        if(user != null) {
            _loginResult.postValue(Result.success(user))
        } else {
            _loginResult.postValue(Result.failure(Exception("Invalid email or password")))
        }
    }

    fun register(userName: String, email: String, password: String) = viewModelScope.launch {
        try {
            val userId = repo.register(userName, email, password)
            _registerResult.postValue(Result.success(userId))
        } catch (e: Exception) {
            _registerResult.postValue(Result.failure(e))
        }
    }

    fun getUserById(userId: Long) {
        viewModelScope.launch {
            try {
                val user = repo.getUserById(userId)
                _currentUser.postValue(user)
            } catch (e: Exception) {
                _currentUser.postValue(null)
            }
        }
    }

    class Factory(private val repo: UserRepository) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(AuthViewModel::class.java)) {
                return AuthViewModel(repo) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }




}