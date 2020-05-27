package com.demo.appko.ui.auth.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.demo.appko.data.UserRepository

public class LoginViewModelFactory(private val userRepository: UserRepository) : ViewModelProvider.Factory{

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass != LoginViewModel::class.java) {
            throw IllegalArgumentException("Unknown ViewModel class")
        }
        val viewModel = LoginViewModel(userRepository)
        return viewModel as T
    }

}
