package com.example.kitchenmate.views

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.Spinner
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.kitchenmate.R
import com.example.kitchenmate.viewModels.CreateFoodActivityViewModel
import com.example.kitchenmate.viewModels.CreateFoodActivityViewModelFactory
import com.example.kitchenmate.databinding.ActivityCreateFoodBinding
import com.example.kitchenmate.databinding.ActivityRegisterBinding
import com.example.kitchenmate.datas.CreateFoodRequest
import com.example.kitchenmate.repositories.AuthRepository
import com.example.kitchenmate.utils.APIService
import com.example.kitchenmate.viewModels.RegisterActivityViewModel
import com.example.kitchenmate.viewModels.RegisterActivityViewModelFactory
import com.example.kitchenmate.repositories.FoodRepository
import com.example.kitchenmate.viewModels.FoodListFragmentViewModel
import com.example.kitchenmate.viewModels.FoodListFragmentViewModelFactory
import android.widget.ArrayAdapter
import androidx.core.view.isVisible

class CreateFoodActivity : AppCompatActivity() {
    var pickedPhoto : Uri? = null  // show address of photo on phone
    var pickedBitMap : Bitmap? = null //can use bitMap to transfer the photo from gallery to app
    private lateinit var imageBitmap: Bitmap
    fun pickPhoto(view: View){
        if (ContextCompat.checkSelfPermission(this,android.Manifest.permission.READ_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED) { // izin alınmadıysa
            ActivityCompat.requestPermissions(this,arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                1)  //if not yet grant permission then set to 1 to request permission
        } else {
            val galeriIntext = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            resultLauncher.launch(galeriIntext)
        }
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == 1) {
            val galeriIntext = Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            resultLauncher.launch(galeriIntext)
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }



    var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK && result.data != null) {
            val data: Intent? = result.data
            if (data != null) {
                pickedPhoto = data.data
//                Log.d("photo URI",pickedPhoto.toString())
            }
            val recipeImageView: ImageView = findViewById<ImageView>(R.id.recipeImageView)
            if (pickedPhoto != null) {
                if (Build.VERSION.SDK_INT >= 28) {
                    val source = ImageDecoder.createSource(this.contentResolver, pickedPhoto!!)
                    pickedBitMap = ImageDecoder.decodeBitmap(source)
//                    Log.d("pickedBitMap URI",pickedBitMap.toString())
                    recipeImageView.setImageBitmap(pickedBitMap)
                    imageBitmap= pickedBitMap as Bitmap
                } else {
                    pickedBitMap =
                        MediaStore.Images.Media.getBitmap(this.contentResolver, pickedPhoto)
                    recipeImageView.setImageBitmap(pickedBitMap)
                    imageBitmap= pickedBitMap as Bitmap
                }
            }
        }
    }
    private lateinit var mBinding: ActivityCreateFoodBinding
    private lateinit var mViewModel: CreateFoodActivityViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)

            mBinding = ActivityCreateFoodBinding.inflate(LayoutInflater.from(this))
            setContentView(R.layout.activity_create_food)
            val create_button = findViewById<Button>(R.id.btnCreate)
            val back_button = findViewById<ImageButton>(R.id.backButton)
            val etFoodName = findViewById<EditText>(R.id.etFoodName)
            val etDescription = findViewById<EditText>(R.id.etDescription)
            val recipe_image = findViewById<ImageView>(R.id.recipeImageView)
            val units = listOf("kg","mL")
            val unit_spinner = findViewById<Spinner>(R.id.unit_spinner)
            var oldDrawable: Drawable? = recipe_image.getDrawable()
            mViewModel = ViewModelProvider(this, CreateFoodActivityViewModelFactory(FoodRepository(APIService.getService(), application), application))[CreateFoodActivityViewModel::class.java]

            ArrayAdapter.createFromResource(
                this,
                R.array.unit_array,
                android.R.layout.simple_spinner_item
            ).also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                unit_spinner.adapter = adapter
            }

            create_button.setOnClickListener {
                val foodName = etFoodName.text.toString()
                val foodUnit = unit_spinner.selectedItem.toString()
                val foodDescription = etDescription.text.toString()
                if(foodName.length == 0){
                    Toast.makeText(this, "Food name cannot be empty!", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener;
                }
                mViewModel.createFood(CreateFoodRequest(name = foodName, amountUnit = foodUnit, description = foodDescription, imageUrl = null, tag = foodName))
                val it = Intent(this, HomeActivity::class.java)
                startActivity(it)
            }

            back_button.setOnClickListener {
                val it = Intent(this, HomeActivity::class.java)
                startActivity(it)
            }
        }

}

