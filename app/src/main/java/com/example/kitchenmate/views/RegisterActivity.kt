package com.example.kitchenmate.views

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.kitchenmate.R
import com.example.kitchenmate.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity(),  View.OnClickListener, View.OnKeyListener, View.OnFocusChangeListener{

    private lateinit var mBinding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityRegisterBinding.inflate(LayoutInflater.from(this))
        setContentView(mBinding.root)
        mBinding.usernameEt.onFocusChangeListener = this
        mBinding.passwordEt.onFocusChangeListener = this
        mBinding.confirmPasswordEt.onFocusChangeListener = this
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

    override fun onClick(p0: View?) {
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
                        }
                    }
                }
            }
        }
    }

}