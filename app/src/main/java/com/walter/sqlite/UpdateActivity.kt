package com.walter.sqlite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class UpdateActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update)

        val inputEditName : EditText = findViewById(R.id.inputEditName)
        val inputEditQuantity : EditText = findViewById(R.id.inputEditQuantity)
        val inputEditPrice : EditText = findViewById(R.id.inputEditPrice)
        val buttonUpdate : Button = findViewById(R.id.buttonEditSave)

        val id  = intent.getIntExtra("id", 0)
        val db = DBHelper(this, null)
        val cursor = db.fetchOneProduct(id)
        cursor!!.moveToFirst()

        inputEditName.setText(cursor.getString(1))
        inputEditQuantity.setText(cursor.getString(2))
        inputEditPrice.setText(cursor.getString(3))

        buttonUpdate.setOnClickListener {
            //update a product in the db
            val name = inputEditName.text.toString().trim()
            val quantity = inputEditQuantity.text.toString().trim().toIntOrNull()
            val price = inputEditPrice.text.toString().trim().toIntOrNull()

            if (name.isNotEmpty() && quantity != null && price !=null){
                db.updateProduct(id, name, quantity, price)
                Toast.makeText(this, "Product $name has been updated", Toast.LENGTH_SHORT).show()
                //navigate back
                finish() //kill the current activity
            }else{
                Toast.makeText(this, "Please enter valid values", Toast.LENGTH_SHORT).show()
            }
        }
    }
}