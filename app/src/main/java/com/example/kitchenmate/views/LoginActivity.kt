package com.example.kitchenmate.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.example.kitchenmate.R
import com.example.kitchenmate.databinding.ActivityLoginBinding
import com.example.kitchenmate.datas.LoginUserRequest
import com.example.kitchenmate.repositories.AuthRepository
import com.example.kitchenmate.utils.APIService
import com.example.kitchenmate.viewModels.LoginActivityViewModel
import com.example.kitchenmate.viewModels.LoginActivityViewModelFactory

class LoginActivity : AppCompatActivity(), View.OnClickListener, View.OnFocusChangeListener {
    private lateinit var mBinding: ActivityLoginBinding
    private lateinit var mViewModel: LoginActivityViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityLoginBinding.inflate(LayoutInflater.from(this))
        setContentView(mBinding.root)
        mBinding.usernameEt.onFocusChangeListener = this
        mBinding.passwordEt.onFocusChangeListener = this
        mBinding.toRegisterText.setOnClickListener(this)
        mBinding.loginBtn.setOnClickListener(this)
        mViewModel = ViewModelProvider(this, LoginActivityViewModelFactory(AuthRepository(APIService.getService()), application))[LoginActivityViewModel::class.java]
        setUpObservers()
    }

    private fun setUpObservers(){
        mViewModel.getIsLoading().observe(this){
            mBinding.progressBar.isVisible = it
        }
        mViewModel.getIsLoginCompleted().observe(this){
            if(it){
                startActivity(Intent(this, RecipeRecyclerViewActivity::class.java))
            }
        }
        mViewModel.getErrorMessage().observe(this){
            if(it.contains("User")){
                mBinding.usernameTil.apply {
                    error = it
                }
            }
            else if(it.contains("password")){
                mBinding.passwordTil.apply {
                    error = it
                }
            }
        }
    }

    private fun validateB4Submission(): Boolean{
        mBinding.usernameTil.apply {
            error = null
        }
        mBinding.passwordTil.apply {
            error = null
        }
        validateUsername()
        validatePw()

        return mBinding.usernameTil.error == null
                && mBinding.passwordTil.error == null
                && mBinding.usernameEt.text!!.isNotEmpty()
                && mBinding.passwordEt.text!!.isNotEmpty()
    }

    override fun onClick(view: View?) {
        if(view == null){
            return
        }

        when(view.id){
            R.id.toRegisterText -> {
                startActivity(Intent(this, RegisterActivity::class.java))
            }
            R.id.loginBtn -> {
                if(validateB4Submission()){
                    mViewModel.loginUser(LoginUserRequest(username = mBinding.usernameEt.text.toString(), password = mBinding.passwordEt.text.toString()))
                }
            }
        }
    }

    private fun validateUsername(): Boolean{
        var errorMsg: String? = null
        val value = mBinding.usernameEt.text.toString()
        if (value.isEmpty()) {
            errorMsg = "Username is required"
        } else if (value.length > 15) {
            errorMsg = "Username cannot exceed 15 characters"
        } else if (!value.matches("[a-zA-Z0-9]+".toRegex())) {
            errorMsg = "Username should contain only letters and numbers"
        }

        if(errorMsg != null){
            mBinding.usernameTil.apply {
                error = errorMsg
            }
        }

        return errorMsg == null
    }

    private fun validatePw(): Boolean{
        var errorMsg: String? = null
        val value = mBinding.passwordEt.text.toString()
        if (value.isEmpty()) {
            errorMsg = "Password is required"
        }

        if(errorMsg != null){
            mBinding.passwordTil.apply {
                error = errorMsg
            }
        }

        return errorMsg == null
    }

    override fun onFocusChange(view: View?, isFocused: Boolean) {
        if(view != null){
            when(view.id){
                R.id.usernameEt -> {
                    if(!isFocused && validateUsername()){
                        mBinding.usernameTil.apply {
                            error = null
                        }
                    }
                }
                R.id.passwordEt -> {
                    if(!isFocused
                        && validatePw()
                        && mBinding.passwordEt.text!!.toString().isNotEmpty()){
                        mBinding.passwordTil.apply {
                            error = null
                        }
                    }
                }
            }
        }
    }
}