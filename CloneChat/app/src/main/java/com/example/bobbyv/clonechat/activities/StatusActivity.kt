package com.example.bobbyv.clonechat.activities
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.bobbyv.clonechat.R
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_status.*

class StatusActivity : AppCompatActivity() {

    var mdata: DatabaseReference?=null
    var curruser: FirebaseUser?=null

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_status)
        supportActionBar!!.title="Status"
        if(intent.extras!=null){
            var oldstat=intent.extras.get("Status")
            statusupdate.setText(oldstat.toString())
        }
        if(intent.extras.equals(null)){
            statusupdate.setText("Enter your new Status")
        }
        changestat.setOnClickListener{

            curruser=FirebaseAuth.getInstance().currentUser
            var userId=curruser!!.uid
            mdata=FirebaseDatabase.getInstance().reference.child("Users").child(userId)
            var status=statusupdate.text.toString().trim()
            mdata!!.child("Status").setValue(status).addOnCompleteListener{
                task: Task<Void> ->
                if(task.isSuccessful){
                    Toast.makeText(this,"Status Updated Successfully", Toast.LENGTH_LONG).show()
                    startActivity(Intent(this,SettingsActivity::class.java))
                }
                else{
                    Toast.makeText(this,"Updation Failed", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}
