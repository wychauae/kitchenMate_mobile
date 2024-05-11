package com.example.kitchenmate.views

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.kitchenmate.R
import com.example.kitchenmate.databinding.ActivityHomeBinding


class HomeActivity : AppCompatActivity() {

    private lateinit var mFragmentManager: FragmentManager
    private lateinit var mBinding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityHomeBinding.inflate(LayoutInflater.from(this))
        setContentView(mBinding.root)
        mFragmentManager = supportFragmentManager

        mBinding.bottomNavigation.background = null
        mBinding.bottomNavigation.setOnItemSelectedListener { item ->
            when(item.itemId){
                R.id.bottomInventory -> openFragment(FoodListFragment())
                R.id.bottomRecipe -> openFragment(RecipeListFragment())
                R.id.bottomBookmark -> openFragment(BookMarkRecipeListFragment())
                R.id.bottomUserProfile -> openFragment(UserProfileFragment())
            }
            true
        }
        openFragment(FoodListFragment())

        val fragment_message = intent.getStringExtra("fragment")
        if(fragment_message == "Recipe"){
            mBinding.bottomNavigation.selectedItemId = R.id.bottomRecipe
            openFragment(RecipeListFragment())
        } else if (fragment_message == "BookMark") {
            mBinding.bottomNavigation.selectedItemId = R.id.bottomBookmark
            openFragment(BookMarkRecipeListFragment())
        } else if (fragment_message === "UserProfile") {
            mBinding.bottomNavigation.selectedItemId =  R.id.bottomUserProfile
            openFragment(UserProfileFragment())
        }
    }

    private fun openFragment(fragment: Fragment){
        val fragmentTransaction: FragmentTransaction = mFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container, fragment)
        fragmentTransaction.commit()
    }


}