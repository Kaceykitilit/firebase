package com.example.afternoonfirebasedatabaseapp

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Email
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {
    lateinit var edtName:EditText
    lateinit var edtEmail:EditText
    lateinit var edtIdNumber:EditText
    lateinit var btnSave:Button
    lateinit var btnView:Button
    lateinit var progressDialog:ProgressDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        edtName=findViewById(R.id.mEdtName)
        edtEmail=findViewById(R.id.mEdtEmail)
        edtIdNumber=findViewById(R.id.mEdtidNumber)
        btnSave=findViewById(R.id.btnsave)
        btnView=findViewById(R.id.btnview)
        progressDialog= ProgressDialog(this)
        progressDialog.setTitle("Saving")
        progressDialog.setMessage("Please wait...")
        btnSave.setOnClickListener {
            var name = edtName.text.toString().trim()
            var email = edtEmail.text.toString().trim()
            var idNumber = edtIdNumber.text.toString().trim()
            var id =System.currentTimeMillis().toString()
            if (name.isEmpty()){
                edtName.setError("Please fill the field")
                edtName.requestFocus()
            }else if(email.isEmpty()){
                edtEmail.setError("Please fill the field")
                edtEmail.requestFocus()
            }else if(idNumber.isEmpty()){
                edtIdNumber.setError("Please fill the field")
                edtIdNumber.requestFocus()
            }else{
                var user = User(name,email,idNumber,id)
                var ref = FirebaseDatabase.getInstance().getReference().child("Users/"+id)
                progressDialog.show()
                ref.setValue(user).addOnCompleteListener {
                    progressDialog.dismiss()
                    if (it.isSuccessful){
                        Toast.makeText(this,"User saved successfully",
                        Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
        btnView.setOnClickListener {
            var intent = Intent(this,UsersActivity::class.java)
            startActivity(intent)

        }
    }

}
