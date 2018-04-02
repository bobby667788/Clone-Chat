package com.example.bobbyv.clonechat.activities

import android.content.Intent
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.example.bobbyv.clonechat.R
import com.example.bobbyv.clonechat.adapter.Secpageadap
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_dashboard.*

class Dashboard : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        supportActionBar!!.title="Dashboard"
        var sectionadap:Secpageadap?=null
        sectionadap=Secpageadap(supportFragmentManager)
        pager.adapter=sectionadap
        tablay.setupWithViewPager(pager)
        tablay.setTabTextColors(Color.WHITE,Color.GREEN)
       // if(intent.extras!=null){
           // var username=intent.extras.get("name")
            //Toast.makeText(this,username.toString(), Toast.LENGTH_LONG).show()
       // }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.main_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        super.onOptionsItemSelected(item)
        if(item!=null){
            if(item.itemId==R.id.logoutid){
                FirebaseAuth.getInstance().signOut()
                startActivity(Intent(this,MainActivity::class.java))
                finish()
            }
            if(item.itemId==R.id.settingsid){
                startActivity(Intent(this,SettingsActivity::class.java))
            }
        }
        return true
    }
}
