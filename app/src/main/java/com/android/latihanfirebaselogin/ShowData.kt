package com.android.latihanfirebaselogin

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ListView
import com.android.latihanfirebaselogin.AdapterC.Adapter
import com.android.latihanfirebaselogin.Add_Data_Act.Add_Data_Act
import com.android.latihanfirebaselogin.Add_Data_Act.Users
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.show_data.*

class ShowData : AppCompatActivity() {
    lateinit var refDb : DatabaseReference
    lateinit var list: MutableList<Users>
    lateinit var listView: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.show_data)
        btnTambah.setOnClickListener {
            val intent = Intent (this,Add_Data_Act::class.java )
            startActivity(intent)
        }

        refDb =FirebaseDatabase.getInstance().getReference("USERS")
        list = mutableListOf()
        listView = findViewById(R.id.ListView)

        refDb.addValueEventListener(
            object : ValueEventListener{
                override fun onDataChange(p0: DataSnapshot) {
                    if (p0!!.exists()){
                        list.clear()
                        for (h in p0.children){
                            val user = h.getValue(Users::class.java)
                            list.add(user!!)
                        }
                        val adapter = Adapter(this@ShowData, R.layout.show_insert, list)
                        listView.adapter = adapter
                    }
                }

                override fun onCancelled(p0: DatabaseError) {


                }
            }
        )
    }
}