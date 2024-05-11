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
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.kitchenmate.R
import com.example.kitchenmate.databinding.ActivityDetailBinding
import com.example.kitchenmate.datas.EditRecipeItem
import com.example.kitchenmate.datas.EditRecipeRequest
import com.example.kitchenmate.datas.InsertRecipeRequest
import com.example.kitchenmate.datas.RecipeIngredient
import com.example.kitchenmate.repositories.RecipeRepository
import com.example.kitchenmate.utils.APIService
import com.example.kitchenmate.utils.AuthToken
import com.example.kitchenmate.viewModels.RecipeDetailActivityViewModel
import com.example.kitchenmate.viewModels.RecipeDetailActivityViewModelFactory

class EditRecipeActivity : AppCompatActivity() {
    var pickedPhoto : Uri? = null  // show address of photo on phone
    var pickedBitMap : Bitmap? = null //can use bitMap to transfer the photo from gallery to app

    private lateinit var mBinding: ActivityDetailBinding
    private lateinit var mViewModel: RecipeDetailActivityViewModel
    private lateinit var recipeID:String


    private val ingredient_rows = arrayListOf<LinearLayout>()
    private val step_rows = arrayListOf<LinearLayout>()
    private lateinit var ingredientContainer:LinearLayout
    private lateinit var stepContainer:LinearLayout


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
            }
            val recipeImageView: ImageView = findViewById<ImageView>(R.id.recipeImageView)
            if (pickedPhoto != null) {
                if (Build.VERSION.SDK_INT >= 28) {
                    val source = ImageDecoder.createSource(this.contentResolver, pickedPhoto!!)
                    pickedBitMap = ImageDecoder.decodeBitmap(source)
                    recipeImageView.setImageBitmap(pickedBitMap)
                } else {
                    pickedBitMap =
                        MediaStore.Images.Media.getBitmap(this.contentResolver, pickedPhoto)
                    recipeImageView.setImageBitmap(pickedBitMap)
                }
            }
        }
    }



    override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_edit_recipe)

            ingredientContainer = findViewById<LinearLayout>(R.id.verticalIngredientInput)
            stepContainer = findViewById<LinearLayout>(R.id.verticalStepInput)

            val save_button = findViewById<Button>(R.id.btnSaveChange)
            val back_button = findViewById<ImageButton>(R.id.backButton)
            val add_ingredient_button = findViewById<ImageButton>(R.id.addIngredientButton)
            val add_step_button = findViewById<ImageButton>(R.id.addStepButton)
            val delete_ingredient_row_button = findViewById<ImageButton>(R.id.deleteButton)
            val delete_step_row_button = findViewById<ImageButton>(R.id.deleteStepButton)


            val recipe_name = findViewById<EditText>(R.id.etRecipeName)

            //remove the default input row
            ingredientContainer.removeView(findViewById<LinearLayout>(R.id.ingredientInput))
            stepContainer.removeView(findViewById<LinearLayout>(R.id.stepInput))



            mBinding = ActivityDetailBinding.inflate(LayoutInflater.from(this))
            mViewModel = ViewModelProvider(this, RecipeDetailActivityViewModelFactory(
                RecipeRepository(
                    APIService.getService(), application), application)
            )[RecipeDetailActivityViewModel::class.java]
            recipeID = intent.getStringExtra("recipeID")!!
            getRecipeDetail()
            setUpObservers()


            delete_ingredient_row_button.setOnClickListener {
                val row = findViewById<LinearLayout>(R.id.ingredientInput)
                ingredientContainer.removeView(row)
                ingredient_rows.remove(row)
            }
            delete_step_row_button.setOnClickListener {
                val row = findViewById<LinearLayout>(R.id.stepInput)
                stepContainer.removeView(row)
                step_rows.remove(row)
            }

            add_ingredient_button.setOnClickListener {
                // Inflate new row layout
                val row = layoutInflater.inflate(R.layout.ingredient_row, null) as LinearLayout
                val delete_button = row.findViewById<ImageButton>(R.id.row_deleteButton)
                // Add row to container
                ingredientContainer.addView(row)
                ingredient_rows.add(row) //track the newly added row without ID

                delete_button.setOnClickListener {
                    ingredientContainer.removeView(row)
                    ingredient_rows.remove(row)
                }
            }
            add_step_button.setOnClickListener {
                // Inflate new row layout
                val row = layoutInflater.inflate(R.layout.step_row, null) as LinearLayout
                val delete_button = row.findViewById<ImageButton>(R.id.rowDeleteStepButton)
                // Add row to container
                stepContainer.addView(row)
                step_rows.add(row) //track the newly added row without ID

                delete_button.setOnClickListener {
                    stepContainer.removeView(row)
                    step_rows.remove(row)
                }
            }

            save_button.setOnClickListener {
                ///get the ingredients
                var recipeName = recipe_name.text.toString()
                if(recipeName.length == 0){
                    Toast.makeText(this, "Recipe name cannot be empty!", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener;
                }

                var finalised_ingredient_rows = ArrayList<RecipeIngredient>()
                for (row in ingredient_rows) {
                    val name_editText = row.getChildAt(0) as EditText
                    val amount_editText = row.getChildAt(1) as EditText
                    val amountUnit_editText = row.getChildAt(2) as EditText
                    if(name_editText.text.toString().length == 0){
                        Toast.makeText(this, "Ingredient name cannot be empty!", Toast.LENGTH_SHORT).show()
                        return@setOnClickListener;
                    }
                    if(amount_editText.text.toString().length == 0){
                        Toast.makeText(this, "Amount cannot be empty!", Toast.LENGTH_SHORT).show()
                        return@setOnClickListener;
                    }
                    if(amountUnit_editText.text.toString().length == 0){
                        Toast.makeText(this, "Unit cannot be empty!", Toast.LENGTH_SHORT).show()
                        return@setOnClickListener;
                    }
                    finalised_ingredient_rows.add(RecipeIngredient(name = name_editText.text.toString(), amount =  Integer.parseInt(amount_editText.text.toString()), amountUnit = amountUnit_editText.text.toString()))
                }

                ///get the steps
                var finalised_step_rows = ArrayList<String>()
                for (row in step_rows) {
                    val editText = row.getChildAt(0) as EditText
                    if(editText.text.toString().length == 0){
                        Toast.makeText(this, "Step cannot be empty!", Toast.LENGTH_SHORT).show()
                        return@setOnClickListener;
                    }
                    finalised_step_rows.add(editText.text.toString())
                }

                val recipe_image = findViewById<ImageView>(R.id.recipeImageView)
                //call API
                //recipe_image.getDrawable().toString()
                mViewModel.editRecipe(
                    EditRecipeRequest(
                        EditRecipeItem(
                            name = recipe_name.text.toString(),
                            steps=finalised_step_rows,
                            ingredients = finalised_ingredient_rows,
                            username = AuthToken.getInstance(application.baseContext).username!!,
                            createType = "recipe",
                            id = recipeID,
                            imageUrl = ""
                        )
                    )
                )
            }
            back_button.setOnClickListener {
                val it = Intent(this, HomeActivity::class.java)
                startActivity(it)
            }
        }
        private fun setUpObservers(){
            Log.d("detail recipeID",
                "here"
            )
            mViewModel.getIsLoading().observe(this){
            }
            mViewModel.getEditRecipeSuccess().observe(this){
                if(it){
                    Toast.makeText(this, "Successfully edit the recipe", Toast.LENGTH_LONG).show()
                    //back to recycler view
                    val it = Intent(this, HomeActivity::class.java)
                    startActivity(it)
                }
            }
            mViewModel.getIsSuccess().observe(this){
    //            var recipeDetailItem = mViewModel.getRecipeDetailItem().value!!
                Log.d("getIsSuccess",
                    it.toString()
                )
                if(it) {
                    mViewModel.getRecipeDetailItem().observe(this) {
                        val recipe_name = findViewById<EditText>(R.id.etRecipeName)
                        val recipe_image = findViewById<ImageView>(R.id.recipeImageView)

                        Log.d("detail foodName", it.name.toString())
                        Log.d("detail photo", it.imageUrl.toString())
                        Log.d("detail ingredient_list", it.ingredients.toString())
                        Log.d("detail step_list", it.steps.toString())
                        Log.d("detail is_Bookmarked", it.isBookmarked.toString())

                        recipe_name.setText(it.name)

                        val imageUrl = APIService.getBaseUrl() + it.imageUrl.replace("\\", "/")
                        Glide.with(findViewById<ImageView>(R.id.recipeImageView)).load(imageUrl).into(recipe_image)


                        //following set the text in the EditText
                        val steps_array: ArrayList<String> = it.steps
                        val ingredientList_array: List<RecipeIngredient> = it.ingredients

                        for (ingredient_row in ingredientList_array) {
                            val row = layoutInflater.inflate(
                                R.layout.ingredient_row,
                                null
                            ) as LinearLayout
                            val delete_button = row.findViewById<ImageButton>(R.id.row_deleteButton)
                            // Add row to container
                            ingredientContainer.addView(row)
                            ingredient_rows.add(row) //track the newly added row without ID

                            val ingredient_editText =
                                row.findViewById<EditText>(R.id.etRecipeIngredientFood)
                            val amonunt_editText =
                                row.findViewById<EditText>(R.id.etRecipeIngredientAmount)
                            val amountUnit_editText =
                                row.findViewById<EditText>(R.id.etRecipeIngredientAmountUnit)

                            ingredient_editText.setText(ingredient_row.name)
                            amonunt_editText.setText(ingredient_row.amount.toString())
                            amountUnit_editText.setText(ingredient_row.amountUnit)

                            delete_button.setOnClickListener {
                                ingredientContainer.removeView(row)
                                ingredient_rows.remove(row)
                            }
                        }
                        for (step in steps_array) {
                            val row =
                                layoutInflater.inflate(R.layout.step_row, null) as LinearLayout
                            val delete_button =
                                row.findViewById<ImageButton>(R.id.rowDeleteStepButton)
                            // Add row to container
                            stepContainer.addView(row)
                            step_rows.add(row) //track the newly added row without ID

                            val stepRow_editText = row.findViewById<EditText>(R.id.etStepRow)
                            stepRow_editText.setText(step)

                            delete_button.setOnClickListener {
                                stepContainer.removeView(row)
                                step_rows.remove(row)
                            }
                        }


                    }
                }
            }

        }

        private fun getRecipeDetail() {
            mViewModel.getRecipeDetail(AuthToken.getInstance(application.baseContext).username!!,recipeID!!)
        }

}