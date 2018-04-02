package com.example.bobbyv.clonechat.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.example.bobbyv.clonechat.R
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_create_account.*

class CreateAccount : AppCompatActivity() {

    var mAuth: FirebaseAuth?=null
    var mdata:DatabaseReference?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_account)
        mAuth= FirebaseAuth.getInstance()
        Createacc.setOnClickListener{
            var email=acemail.text.toString().trim()
            var pass=acpass.text.toString().trim()
            var name=acname.text.toString().trim()

            if(!TextUtils.isEmpty(email)||!TextUtils.isEmpty(name)||!TextUtils.isEmpty(pass)){
                createAccount(name,pass,email)
            }
            else{
                Toast.makeText(this,"Please fill all the details",Toast.LENGTH_LONG).show()
            }
        }
    }

    fun createAccount(name: String,pass:String,mail:String){
        this.mAuth!!.createUserWithEmailAndPassword(mail,pass).addOnCompleteListener(this,{
            task:Task<AuthResult> ->
            if(task.isSuccessful){
                var curruser=mAuth!!.currentUser
                var userid=curruser!!.uid

                var uobject=HashMap<String,String>()
                uobject.put("Display_name",name)
                uobject.put("Status","Hi I'm New")
                uobject.put("image","default")
                uobject.put("thumb_image","default")

                mdata=FirebaseDatabase.getInstance().reference.child("Users").child(userid)
                mdata!!.setValue(uobject).addOnCompleteListener{
                    task:Task<Void>  ->
                    if(task.isSuccessful){
                        Toast.makeText(this,"User Created",Toast.LENGTH_LONG).show()
                        var dashintent=Intent(this, Dashboard::class.java)
                         dashintent.putExtra("name",name)
                        startActivity(dashintent)
                        finish()
                    }
                    else{
                        Toast.makeText(this,"OOPS!! User not Created",Toast.LENGTH_LONG).show()
                    }
                }
            }else{
                Toast.makeText(this,"OOPS!! Somethings wrong",Toast.LENGTH_LONG).show()
            }
        })
    }
}
