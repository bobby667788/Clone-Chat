package com.example.bobbyv.clonechat.adapter

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.provider.ContactsContract
import android.support.v7.view.menu.ActionMenuItemView
import android.support.v7.widget.RecyclerView
import android.view.TextureView
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.example.bobbyv.clonechat.R
import com.example.bobbyv.clonechat.activities.Chat
import com.example.bobbyv.clonechat.activities.Profile
import com.example.bobbyv.clonechat.models.User
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView

/**
 * Created by Bobby on 22-02-2018.
 */
class Useradapt(databaseQuery:DatabaseReference,var context:Context) :FirebaseRecyclerAdapter
<User,Useradapt.ViewHolder>(User::class.java,
        R.layout.users_bob,Useradapt.ViewHolder::class.java,databaseQuery){

    override fun populateViewHolder(viewHolder: Useradapt.ViewHolder?, user: User?, position: Int) {

        var userid=getRef(position).key
        viewHolder!!.bindview(user!!,context)
        viewHolder.itemView.setOnClickListener {
            var option= arrayOf("Open Profile","Send Message")
            var builder=AlertDialog.Builder(context)
            builder.setTitle("Select Options")
            builder.setItems(option,DialogInterface.OnClickListener{dialogInterface, i ->  
                var uname=viewHolder.usernametxt
                var ustat=viewHolder.userstattxt
                var uprof=viewHolder.userproftxt

                if(i==0){
                    var prointent=Intent(context,Profile::class.java)
                    prointent.putExtra("userid",userid)
                    context.startActivity(prointent)
                }
                else{
                    var chintent=Intent(context,Chat::class.java)
                    chintent.putExtra("userid",userid)
                    chintent.putExtra("name",uname)
                    chintent.putExtra("status",ustat)
                    chintent.putExtra("profile",uprof)
                    context.startActivity(chintent)
                }
            })
            builder.show()
        }
    }

    class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        var usernametxt:String?=null
        var userstattxt:String?=null
        var userproftxt:String?=null

        fun bindview(user:User,context: Context){
            var username=itemView.findViewById<TextView>(R.id.username)
            var userstat=itemView.findViewById<TextView>(R.id.userstat)
            var userprof=itemView.findViewById<CircleImageView>(R.id.userpro)

            usernametxt=user.Display_name
            userstattxt=user.Status
            userproftxt=user.image
            username.text=user.Display_name
            userstat.text=user.Status

            Picasso.with(context).load(userproftxt).placeholder(R.drawable.profile).into(userprof)
        }
    }
}