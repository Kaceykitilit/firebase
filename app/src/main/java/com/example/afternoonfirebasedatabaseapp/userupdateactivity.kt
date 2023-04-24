package com.example.afternoonfirebasedatabaseapp

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Email
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase

class userupdateactivity : AppCompatActivity() {
    lateinit var jina:EditText
    lateinit var mail:EditText
    lateinit var idno:EditText
    lateinit var upd:Button
    lateinit var progressDialog: ProgressDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_userupdateactivity)
        jina = findViewById(R.id.mEdtName)
        mail= findViewById(R.id.mEdtEmail)
        idno=findViewById(R.id.mEdtidNumber)
        upd=findViewById(R.id.mbtnupdate)
        progressDialog= ProgressDialog(this)
        progressDialog.setTitle("Updating")
        progressDialog.setMessage("Please wait")

        var receivedName= intent.getStringExtra("name")
        var receivedEmail= intent.getStringExtra("Email")
        var receivedIdNumber= intent.getStringExtra("idNumber")
        var receivedId= intent.getStringExtra("id")

        jina.setText(receivedName)
        mail.setText(receivedEmail)
        idno.setText(receivedId)

        upd.setOnClickListener {
            var name = jina.text.toString().trim()
            var email= mail.text.toString().trim()
            var idNumber=idno.text.toString().trim()
            var id = receivedId!!

            if (name.isEmpty()){
                jina.setError("Please fill this field")
                jina.requestFocus()


            }else if(email.isEmpty()){
                mail.setError("Please fill the field")
                mail.requestFocus()
            }else if(idNumber.isEmpty()){
                idno.setError("Please fill the field")
                idno.requestFocus()
            }else{
                var user =User(name, email, idNumber, id)
                var ref = FirebaseDatabase.getInstance().getReference().child("Users/"+id)
                progressDialog.show()
                ref.setValue(user).addOnCompleteListener{
                    progressDialog.dismiss()
                    if (it.isSuccessful){
                        Toast.makeText(this,"User updated successfully",
                        Toast.LENGTH_LONG).show()
                        startActivity(Intent(this@userupdateactivity,UsersActivity::class.java))
                        finish()

                    }else{
                        Toast.makeText(this,"User update failed",
                            Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }
}