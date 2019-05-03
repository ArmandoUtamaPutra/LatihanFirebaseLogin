package com.android.latihanfirebaselogin.AdapterC

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.android.latihanfirebaselogin.Add_Data_Act.Users
import com.android.latihanfirebaselogin.R
import com.android.latihanfirebaselogin.ShowData
import com.google.firebase.database.FirebaseDatabase

class Adapter (val mCtx: Context, val layoutResId: Int, val list: List<Users> )
    :ArrayAdapter<Users>(mCtx, layoutResId, list){
    override fun getView(position: Int, convertView: View? , parent:ViewGroup): View {

        val layoutInflater : LayoutInflater = LayoutInflater.from(mCtx)
        val view: View = layoutInflater.inflate(layoutResId,null)

        val textName =view.findViewById<TextView>(R.id.TextNama)
        val textStatus = view.findViewById<TextView>(R.id.TextStatus)

        val textUpdate = view.findViewById<TextView>(R.id.tvUpdate)
        val textDelete = view.findViewById<TextView>(R.id.tvDelete)


        val users =list[position]

        textName.text = users.nama
        textStatus.text = users.status

        textUpdate.setOnClickListener {
            showUpdateDialog(users)
        }
        textDelete.setOnClickListener {
            Deleteinfo(users)
        }


        return view
    }
    private fun Deleteinfo(user: Users) {
        val progressDialog = ProgressDialog(context, R.style.AppTheme)
        progressDialog.isIndeterminate = true
        progressDialog.setMessage("Deleting...")
        progressDialog.show()
        val mydatabase = FirebaseDatabase.getInstance().getReference("USERS")
        mydatabase.child(user.id).removeValue()
        Toast.makeText(
            mCtx, "Deleted!!", Toast.LENGTH_SHORT
        ).show()
        val intent = Intent(context, ShowData::class.java)
        context.startActivity(intent)
    }
    private fun showUpdateDialog(user : Users){
        val builder = AlertDialog.Builder(mCtx)
        builder.setTitle("Update")
        val inflater = LayoutInflater.from(mCtx)
        val view = inflater.inflate(R.layout.update_data, null)
        val textNama = view.findViewById<EditText>( R.id.tvName)
        val textStatus = view.findViewById<EditText>(R.id.tvStatus)

        textNama.setText(user.nama)
        textStatus.setText(user.status)
        builder.setView(view)
        builder.setPositiveButton("update"){
            dialog, which ->

            val dbUsers = FirebaseDatabase.getInstance().getReference("USERS")
            val nama=textNama.text.toString().trim()
            val status=textStatus.text.toString().trim()
            if (nama.isEmpty()){
                textNama.error = "please enter name"
                textNama.requestFocus()
                return@setPositiveButton
            }
            if (nama.isEmpty()){
                textStatus.error = "please enter status"
                textStatus.requestFocus()
                return@setPositiveButton
            }
            val  user = Users(user.id,nama,status)

            dbUsers.child(user.id).setValue(user).addOnCompleteListener {
                Toast.makeText(mCtx, "Update",
                    Toast.LENGTH_SHORT).show()
            }
        }
        builder.setNegativeButton("No"){
            dialog, which ->
        }
        val alert = builder.create()
        alert.show()
    }
}
