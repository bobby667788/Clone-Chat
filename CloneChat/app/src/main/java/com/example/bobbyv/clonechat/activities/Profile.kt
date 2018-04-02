package com.example.bobbyv.clonechat.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.bobbyv.clonechat.R
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_profile.*

class Profile : AppCompatActivity() {

    var muser:FirebaseUser?=null
    var mdata:DatabaseReference?=null
    var userid:String?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        supportActionBar!!.title="Profile"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        if(intent.extras!=null){
            userid=intent.extras.get("userid").toString()
            mdata= FirebaseDatabase.getInstance().reference.child("Users").child(userid)
            setUpProfile()
        }
    }

    private fun setUpProfile(){
        mdata!!.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(datasnap: DataSnapshot?) {
                var dispname=datasnap!!.child("Display_name").value.toString()
                var status=datasnap!!.child("Status").value.toString()
                var image=datasnap!!.child("image").value.toString()

                proname.text=dispname
                prostat.text=status
                Picasso.with(this@Profile).load(image).placeholder(R.drawable.witcher).into(propic)

            }

            override fun onCancelled(p0: DatabaseError?) {

            }
        })
    }

}
