package com.example.anotherregisterapp.database.viewModels

import android.util.Log
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

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun login(email: String, password:String) {
        if(email.isBlank() || password.isBlank()) {
            _loginResult.postValue(Result.failure(IllegalArgumentException("Email and password cannot be empty")))
        }
        _isLoading.postValue(true)
        viewModelScope.launch{
            try{
                val user = repo.login(email, password)
                if(user != null){
                    _currentUser.postValue(user)
                    _loginResult.postValue(Result.success(user))
                    Log.d("AuthViewModel", "Login successful for user: ${user.userName}")
                }else{
                    _loginResult.postValue(Result.failure(Exception("Invalid email or password")))
                    Log.d("AuthViewModel", "Login failed for email: $email")
                }
            }catch (e: Exception) {
                _loginResult.postValue(Result.failure(e))
                Log.e("AuthViewModel", "Login error: ${e.message}")
            } finally {
                _isLoading.postValue(false)
            }
        }
    }



    fun register(userName: String, email: String, password: String) {
        if(userName.isBlank() || email.isBlank() || password.isBlank()) {
            _registerResult.postValue(Result.failure(IllegalArgumentException("All fields are required")))
            return
        }

        _isLoading.postValue(true)
        viewModelScope.launch {
            try{
                val userId = repo.register(userName, email, password)
                _registerResult.postValue(Result.success(userId))
                Log.d("AuthViewModel", "Registration successful for user: $userName")
            }catch (e: Exception){
                _registerResult.postValue(Result.failure(e))
                Log.e("AuthViewModel", "Registration error: ${e.message}")
            } finally {
                _isLoading.postValue(false)
            }
        }
    }

    fun getUserById(userId: Long) {
        viewModelScope.launch {
            try {
                val user = repo.getUserById(userId)
                _currentUser.postValue(user)
            } catch (e: Exception) {
                _currentUser.postValue(null)
                Log.e("AuthViewModel", "Error at getUserById: ${e.message}")
            }
        }
    }

    fun logout() {
        _currentUser.postValue(null)
        _loginResult.postValue(Result.failure(Exception("Section expired or user logged out")))
        Log.d("AuthViewModel", "User logged out")
    }

    fun clearResults() {
            _loginResult.postValue(Result.failure(Exception("Cleared")))
            _registerResult.postValue(Result.failure(Exception("Cleared")))
        }

    fun isUserLoggedIn(): Boolean {
        return _currentUser.value != null
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