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
import kotlinx.android.synthetic.main.activity_login.*

class Login : AppCompatActivity() {

    var mAuth: FirebaseAuth?=null
    var mdata: DatabaseReference?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        mAuth= FirebaseAuth.getInstance()

        loginbut.setOnClickListener{
            var email=loginemail.text.toString().trim()
            var password=password.text.toString().trim()
            if(!TextUtils.isEmpty(email)||!TextUtils.isEmpty(password)){
                loginuser(email,password)
            }
           else{
                Toast.makeText(this,"Please Enter All Fields", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun loginuser(email: String, pass: String) {
        this.mAuth!!.signInWithEmailAndPassword(email,pass).addOnCompleteListener(this,{
            task: Task<AuthResult> ->
            if(task.isSuccessful){
                var username=email.split("@")[0]

                var dashboard=Intent(this, Dashboard::class.java)
                dashboard.putExtra("name",username)
                startActivity(dashboard)
                finish()
            }
            else{
                Toast.makeText(this,"Login Failed!", Toast.LENGTH_LONG).show()
            }
        })
    }
}
