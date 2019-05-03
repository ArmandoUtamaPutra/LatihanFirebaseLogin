package com.android.latihanfirebaselogin.Add_Data_Act

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.android.latihanfirebaselogin.R
import com.android.latihanfirebaselogin.ShowData
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.add_data.*

class Add_Data_Act : AppCompatActivity(){
    lateinit var  refDb : DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_data)

        refDb =FirebaseDatabase.getInstance().getReference("USERS")
        btnSave.setOnClickListener {
            saveData()
            val intent = Intent(this, ShowData::class.java)
            startActivity(intent)
        }
    }
    private fun saveData() {
        val nama = InputNama.text.toString()
        val status = InputStatus.text.toString()

        val UserId = refDb.push().key.toString()
        val user = Users(UserId, nama, status)

        refDb.child(UserId).setValue(user).addOnCompleteListener {
            Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
            InputNama.setText("")
            InputStatus.setText("")
        }
    }
}