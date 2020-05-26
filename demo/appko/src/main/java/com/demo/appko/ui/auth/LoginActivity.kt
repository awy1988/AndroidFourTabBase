package com.demo.appko.ui.auth

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.demo.appko.R
import com.demo.appko.databinding.LoginActBinding
import com.demo.appko.ui.AppKoMainActivity
import com.demo.appko.ui.auth.viewmodel.LoginViewModel
import com.demo.appko.ui.base.BaseFragmentActivity
import com.demo.corelib.util.KeyboardUtils

class LoginActivity : BaseFragmentActivity() {

    private lateinit var binding: LoginActBinding
    private val viewModel: LoginViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.login_act)
        binding.lifecycleOwner = this

        binding.viewModel = viewModel
        binding.loginActivity = this
        binding.toolbar.title = "登录"

        initObserver()

    }

    fun onLoginClick(view: View) {
        viewModel.login()
    }

    fun onRefreshCaptchaClick(view: View) {
        Toast.makeText(this,"word",Toast.LENGTH_LONG).show()
    }

    private fun initObserver() {
        viewModel.errorMessage.observe(this, Observer {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        })

        viewModel.loginSuccess.observe(this, Observer {
            if (it) {
                KeyboardUtils.hideKeyboard(this)
                startActivity(Intent(this, AppKoMainActivity::class.java))
            }
        })

    }

}
