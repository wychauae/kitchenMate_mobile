package com.example.kitchenmate.views

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.kitchenmate.databinding.FragmentUserProfileBinding
import com.example.kitchenmate.utils.AuthToken
import com.example.kitchenmate.R

class UserProfileFragment : Fragment() {

    private lateinit var mBinding: FragmentUserProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        val message = arguments?.getString("fragment message")
//        Log.d("message is", message.toString())
//        if(message == "UserProfileFragment"){
//            startActivity(Intent(requireContext(), MyRecipeListActivity::class.java))
//        }

        mBinding = FragmentUserProfileBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding.usernameTv.text = AuthToken.getInstance(requireContext().applicationContext).username!!
        mBinding.myRecipeBtn.setOnClickListener { handleMyRecipeButtonClick() }
        mBinding.myShoppingListBtn.setOnClickListener { handleMyShoppingListButtonClick() }
        mBinding.accountSettingBtn.setOnClickListener { handleAccountSettingButtonClick() }
        mBinding.notificationBtn.setOnClickListener { handleNotificationButtonClick() }
        mBinding.logOutBtn.setOnClickListener { handleLogOutButtonClick() }

    }

    private fun handleMyRecipeButtonClick() {
        startActivity(Intent(requireContext(), MyRecipeListActivity::class.java))
    }

    private fun handleMyShoppingListButtonClick() {
        // Handle myShoppingListBtn click
    }

    private fun handleAccountSettingButtonClick() {
        // Handle accountSettingBtn click
    }

    private fun handleNotificationButtonClick() {
        // Handle notificationBtn click
    }

    private fun handleLogOutButtonClick() {
        val alertDialogBuilder = AlertDialog.Builder(requireContext())
            .setTitle("Logout")
            .setMessage("Are you sure you want to log out?")
            .setPositiveButton("Yes") { dialog, which ->
                AuthToken.getInstance(requireContext().applicationContext).token = null
                AuthToken.getInstance(requireContext().applicationContext).username = null
                startActivity(Intent(requireContext(), LoginActivity::class.java))
            }
            .setNegativeButton("No") { dialog, which ->
                // User clicked "No", do nothing
            }

        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }
}