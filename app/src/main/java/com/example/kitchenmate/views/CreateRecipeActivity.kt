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
import android.view.View
import android.widget.Button
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
import com.example.kitchenmate.R

class CreateRecipeActivity : AppCompatActivity() {
    var pickedPhoto : Uri? = null  // show address of photo on phone
    var pickedBitMap : Bitmap? = null //can use bitMap to transfer the photo from gallery to app

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
            setContentView(R.layout.activity_create_recipe)

            val create_button = findViewById<Button>(R.id.btnIngredient)
            val back_button = findViewById<ImageButton>(R.id.backButton)
            val add_ingredient_button = findViewById<ImageButton>(R.id.addIngredientButton)
            val add_step_button = findViewById<ImageButton>(R.id.addStepButton)
            val delete_ingredient_row_button = findViewById<ImageButton>(R.id.deleteButton)
            val delete_step_row_button = findViewById<ImageButton>(R.id.deleteStepButton)


            val recipe_name = findViewById<EditText>(R.id.etRecipeName)
            val recipe_image = findViewById<ImageView>(R.id.recipeImageView)

            var oldDrawable: Drawable? = recipe_image.getDrawable()


            val ingredient_rows = arrayListOf<LinearLayout>(findViewById<LinearLayout>(R.id.ingredientInput))
            val step_rows = arrayListOf<LinearLayout>(findViewById<LinearLayout>(R.id.stepInput))
            val ingredientContainer = findViewById<LinearLayout>(R.id.verticalIngredientInput)
            val stepContainer = findViewById<LinearLayout>(R.id.verticalStepInput)

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

            create_button.setOnClickListener {
                ///get the ingredients
                var recipeName = recipe_name.text.toString()
                if(recipeName.length == 0){
                    Toast.makeText(this, "Recipe name cannot be empty!", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener;
                }

                if(recipe_image.getDrawable() == oldDrawable){
                    Toast.makeText(this, "Recipe image cannot be empty!", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener;
                }

                var finalised_ingredient_rows =""
                if(findViewById<EditText>(R.id.etRecipeIngredientFood).text.toString().length == 0){
                    Toast.makeText(this, "Ingredient name cannot be empty!", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener;
                }
                if(findViewById<EditText>(R.id.etRecipeIngredientAmount).text.toString().length == 0){
                    Toast.makeText(this, "Amount cannot be empty!", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener;
                }
                if(findViewById<EditText>(R.id.etRecipeIngredientAmountUnit).text.toString().length == 0){
                    Toast.makeText(this, "Unit cannot be empty!", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener;
                }
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
                    finalised_ingredient_rows += (name_editText.text.toString() + amount_editText.text.toString()+ amountUnit_editText.text.toString())
                }

                ///get the steps
                var finalised_step_rows = ""
                if(findViewById<EditText>(R.id.etRecipeStep).text.toString().length == 0){
                    Toast.makeText(this, "Step cannot be empty!", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener;
                }
                for (row in step_rows) {
                    val editText = row.getChildAt(0) as EditText
                    if(editText.text.toString().length == 0){
                        Toast.makeText(this, "Step cannot be empty!", Toast.LENGTH_SHORT).show()
                        return@setOnClickListener;
                    }
                    finalised_step_rows += editText.text.toString()
                }

                //call API

                Toast.makeText(this, recipeName+" "+finalised_ingredient_rows+" "+finalised_step_rows, Toast.LENGTH_LONG).show()

                //back to recycler view
                val it = Intent(this, RecipeRecyclerViewActivity::class.java)
                startActivity(it)
            }
            back_button.setOnClickListener {
                val it = Intent(this, RecipeRecyclerViewActivity::class.java)
                startActivity(it)
            }
        }
}