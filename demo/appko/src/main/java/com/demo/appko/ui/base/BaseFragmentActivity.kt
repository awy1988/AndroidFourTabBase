package com.demo.appko.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatViewInflater
import com.demo.widget.CustomProgressDialog

open class BaseFragmentActivity : AppCompatActivity() {

    private lateinit var progressDialog: CustomProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        progressDialog = CustomProgressDialog(this, "努力加载中...")
        progressDialog.setCancelable(false)
    }

    protected fun showLoadingDialog() {
        showProgressBar(true)
    }

    protected fun hideLoadingDialog() {
        showProgressBar(false)
    }



    private fun showProgressBar(show: Boolean) {
        if (show) {
            if(progressDialog.isShowing) {
                progressDialog.dismiss()
            }
            if(!progressDialog.isShowing) {
                progressDialog.show()
            }
        } else {
            if (progressDialog.isShowing) {
                progressDialog.dismiss()
            }
        }
    }
}
