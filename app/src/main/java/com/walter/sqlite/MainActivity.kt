package com.walter.sqlite

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val inputName: EditText = findViewById(R.id.inputName)
        val inputQuantity: EditText = findViewById(R.id.inputQuantity)
        val inputPrice: EditText = findViewById(R.id.inputPrice)
        val buttonSave: Button = findViewById(R.id.buttonSave)
        val buttonShow: Button = findViewById(R.id.buttonShow)
        val buttonDelete: Button = findViewById(R.id.buttonDelete)
        val buttonFetchOne: Button = findViewById(R.id.buttonFetchOne)

        //Database
        val db = DBHelper(this, null)

        buttonSave.setOnClickListener {
            val name = inputName.text.toString().trim()
            val quantity = inputQuantity.text.toString().trim().toIntOrNull()
            val price = inputPrice.text.toString().trim().toIntOrNull()
            if (quantity != null && price != null && name.isNotEmpty()) {
                //save to our database
                db.addProduct(name, price, quantity)

                //clear input
                inputName.text.clear()
                inputQuantity.text.clear()
                inputPrice.text.clear()

                Toast.makeText(this, "Product Added", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Enter valid values", Toast.LENGTH_SHORT).show()
            }
        }


//CRUD  Create Read Update Delete
        buttonShow.setOnClickListener {
            val intent = Intent(this, DisplayActivity::class.java)
            startActivity(intent)
           /* val products  = db.getProducts() //cursor
            while (products!!.moveToNext()){
                //4 - 0, 1,
                val id = products.getInt(0)
                val name = products.getString(1)
                val quantity = products.getInt(2)
                val price = products.getInt(3)
                Log.d("PRODUCT", "$id : $name : $quantity : $price")
                Toast.makeText(this, "$id : $name : $quantity : $price", Toast.LENGTH_SHORT).show()
            }
            db.close()*/
        }

        buttonDelete.setOnClickListener {
            db.deleteProduct(1)
            //Update
            db.updateProduct(5, "Basmati Rice",55, 70)// 5 : Rice : 50 : 65

            //search
            val result = db.searchProducts("ice")
            val count = result!!.count
            Log.d("PRODUCT", "We found $count products")
        }

        buttonFetchOne.setOnClickListener {
            val p = db.fetchOneProduct(6)
            p!!.moveToFirst()
            val name = p.getString(1)
            Toast.makeText(this, name, Toast.LENGTH_SHORT).show()
        }

        //hide buttons
        buttonDelete.visibility = View.GONE
        buttonFetchOne.visibility = View.GONE

        //DisplayActivity
        //UpdateActivity

    }
}