package com.example.kitchenmate.views

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.example.kitchenmate.R
import com.example.kitchenmate.databinding.ActivityRegisterBinding
import com.example.kitchenmate.datas.RegisterUserRequest
import com.example.kitchenmate.repositories.AuthRepository
import com.example.kitchenmate.utils.APIService
import com.example.kitchenmate.viewModels.RegisterActivityViewModel
import com.example.kitchenmate.viewModels.RegisterActivityViewModelFactory

class RegisterActivity : AppCompatActivity(),  View.OnClickListener, View.OnKeyListener, View.OnFocusChangeListener{

    private lateinit var mBinding: ActivityRegisterBinding
    private lateinit var mViewModel: RegisterActivityViewModel
    private val TAG: String = "RegisterActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityRegisterBinding.inflate(LayoutInflater.from(this))
        setContentView(mBinding.root)
        mBinding.usernameEt.onFocusChangeListener = this
        mBinding.passwordEt.onFocusChangeListener = this
        mBinding.confirmPasswordEt.onFocusChangeListener = this
        mBinding.registerBtn.setOnClickListener(this)
        mViewModel = ViewModelProvider(this, RegisterActivityViewModelFactory(AuthRepository(
            APIService.getService()), application))[RegisterActivityViewModel::class.java]
        setUpObservers()
    }

    private fun setUpObservers(){
        mViewModel.getIsLoading().observe(this){
            mBinding.progressBar.isVisible = it
        }
        mViewModel.getIsRegisterCompleted().observe(this){
            if(it){
                Log.d(TAG, "registered!!!!!!!!!")
                startActivity(Intent(this, LoginActivity::class.java))
            }
        }
        mViewModel.getErrorMessage().observe(this){
            if(it.contains("username")){
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

    private fun validateUsername(): Boolean{
        var errorMsg: String? = null
        val value = mBinding.usernameEt.text.toString()
        if(value.isEmpty()){
            errorMsg = "Username is required"
        }

        if(errorMsg != null){
            mBinding.usernameEt.apply {
                error = errorMsg
            }
        }

        return errorMsg == null
    }

    private fun validatePw(): Boolean{
        var errorMsg: String? = null
        val value = mBinding.passwordEt.text.toString()
        if(value.isEmpty()){
            errorMsg = "Password is required"
        }
        else if(value.length < 6){
            errorMsg = "Password must have length >= 6"
        }

        if(errorMsg != null){
            mBinding.passwordEt.apply {
                error = errorMsg
            }
        }

        return errorMsg == null
    }

    private fun validateConfirmPw(): Boolean{
        var errorMsg: String? = null
        val value = mBinding.confirmPasswordEt.text.toString()
        val pw = mBinding.passwordEt.text.toString()
        if(value.isEmpty()){
            errorMsg = "This field is required"
        }
        else if(pw != value){
            errorMsg = "Not the same as password"
        }

        if(errorMsg != null){
            mBinding.confirmPasswordEt.apply {
                error = errorMsg
            }
        }

        return errorMsg == null
    }

    private fun validateB4Submission(): Boolean{
        return mBinding.usernameTil.error == null
                && mBinding.passwordTil.error == null
                && mBinding.confirmPasswordTil.error == null
                && mBinding.usernameEt.text!!.isNotEmpty()
                && mBinding.passwordEt.text!!.isNotEmpty()
                && mBinding.confirmPasswordEt.text!!.isNotEmpty()
    }

    override fun onClick(view: View?) {
        Log.d(TAG, "click")

        if(view == null){
            return
        }


        when(view.id){
            R.id.registerBtn -> {
                mBinding.usernameEt.clearFocus()
                mBinding.passwordEt.clearFocus()
                mBinding.confirmPasswordEt.clearFocus()

                if(validateB4Submission()){
                    mViewModel.registerUser(
                        RegisterUserRequest(
                            username = mBinding.usernameEt.text.toString(),
                            password = mBinding.confirmPasswordEt.text.toString())
                    )
                }
            }
        }
    }

    override fun onKey(p0: View?, p1: Int, p2: KeyEvent?): Boolean {
        return false
    }

    override fun onFocusChange(view: View?, isFocused: Boolean) {
        if(view != null){
            when(view.id){
                R.id.usernameEt -> {
                    if(!isFocused){
                        validateUsername()
                    }
                }
                R.id.passwordEt -> {
                    if(!isFocused
                        && validatePw()){
                        mBinding.passwordTil.apply {
                            error = null
                            setStartIconDrawable(R.drawable.baseline_check_circle_24)
                            setStartIconTintList(ColorStateList.valueOf(Color.GREEN))
                        }
                    }
                }
                R.id.confirmPasswordEt -> {
                    if(!isFocused
                        && validatePw()
                        && mBinding.confirmPasswordEt.text!!.toString().isNotEmpty()
                        && validateConfirmPw()){
                        mBinding.confirmPasswordTil.apply {
                            setStartIconDrawable(R.drawable.baseline_check_circle_24)
                            setStartIconTintList(ColorStateList.valueOf(Color.GREEN))
                            error = null
                        }
                    }
                }
            }
        }
    }

}