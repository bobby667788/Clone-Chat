package com.example.bobbyv.clonechat.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.bobbyv.clonechat.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var mAuth:FirebaseAuth?=null
    var user:FirebaseUser?=null
    var authlistener:FirebaseAuth.AuthStateListener?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mAuth=FirebaseAuth.getInstance()
        authlistener=FirebaseAuth.AuthStateListener{
            firebaseAuth: FirebaseAuth ->
            user=firebaseAuth.currentUser
            if(user!=null){
                startActivity(Intent(this, Dashboard::class.java))
                finish()
            }
            else{
                Toast.makeText(this,"Not Signed In!",Toast.LENGTH_LONG).show()
            }
        }
        createa.setOnClickListener{
            startActivity(Intent(this, CreateAccount::class.java))
        }
        login.setOnClickListener{
            startActivity(Intent(this, Login::class.java))
        }
    }

    override fun onStart() {
        super.onStart()
        this.mAuth!!.addAuthStateListener(authlistener!!)
    }

    override fun onStop() {
        super.onStop()
        if(authlistener!=null){
            this.mAuth!!.removeAuthStateListener(authlistener!!)
        }
    }
}
