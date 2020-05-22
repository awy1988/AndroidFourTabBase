package com.demo.appko.ui.auth

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.demo.appko.R
import com.demo.appko.databinding.LoginActBinding
import com.demo.appko.ui.auth.viewmodel.LoginViewModel
import com.demo.appko.ui.base.BaseFragmentActivity

class LoginActivity : BaseFragmentActivity() {

    private lateinit var binding: LoginActBinding
    val viewModel: LoginViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.login_act)
        binding.lifecycleOwner = this

        binding.viewModel = viewModel
        binding.loginActivity = this
    }

    fun onLoginClick(view: View) {

    }

    fun onRefreshCaptchaClick(view: View) {

    }

}
