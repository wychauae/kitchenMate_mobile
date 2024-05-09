package com.example.kitchenmate.views

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.kitchenmate.R
import com.example.kitchenmate.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var mFragmentManager: FragmentManager
    private lateinit var mBinding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(mBinding.root)

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
        mFragmentManager = supportFragmentManager
        openFragment(FoodListFragment())

    }

    private fun openFragment(fragment: Fragment){
        val fragmentTransaction: FragmentTransaction = mFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container, fragment)
        fragmentTransaction.commit()
    }

}