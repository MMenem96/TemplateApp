package com.mywork.templateapp.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.mywork.templateapp.base.BaseActivity
import com.mywork.templateapp.databinding.ActivityLoginBinding
import com.mywork.templateapp.viewmodels.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : BaseActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val viewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        observeData()
    }

    private fun observeData() {
        viewModel.login("888").observe(this, Observer { observe ->
            if (observe.data?.response?.getContentIfNotHandled()?.message == "Logged In") {

            }

//            if (observe.loading.isLoading) {
//
//            } else {
//
//            }

        })
    }

    override fun showProgressBar(show: Boolean) {

    }
}