package com.example.bobbyv.clonechat.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.view.menu.ActionMenuItemView
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.example.bobbyv.clonechat.R
import com.example.bobbyv.clonechat.models.Friendm
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.activity_chat.*
import kotlinx.android.synthetic.main.bar_at_top.*
import kotlinx.android.synthetic.main.bar_at_top.view.*

class Chat : AppCompatActivity() {

    var userid:String?=null
    var mdata:DatabaseReference?=null
    var fuser:FirebaseUser?=null
    var lmanage:LinearLayoutManager?=null
    var mfadap:FirebaseRecyclerAdapter<Friendm,Messagehold>?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        fuser=FirebaseAuth.getInstance().currentUser
        userid=intent.extras.getString("userid")
        var profimglink=intent.extras.get("profile").toString()
        lmanage= LinearLayoutManager(this)
        lmanage!!.stackFromEnd=true
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowCustomEnabled(true)

        var inflater=this.getSystemService(android.content.Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        var actionbar=inflater.inflate(R.layout.bar_at_top,null)
        actionbar.barname.text=intent.extras.getString("name")
        Picasso.with(this).load(profimglink).placeholder(R.drawable.profile).into(actionbar.barcircleid)
        mdata=FirebaseDatabase.getInstance().reference
        supportActionBar!!.customView=actionbar
        mfadap=object :FirebaseRecyclerAdapter<Friendm,Messagehold>

        (Friendm::class.java,R.layout.message_chat,Messagehold::class.java,mdata!!.child("messages")){

            override fun populateViewHolder(viewHolder: Messagehold?, friend: Friendm?, position: Int) {
                if(friend!!.text!=null){
                    viewHolder!!.bindView(friend)
                    var curruser=fuser!!.uid

                    var me:Boolean=friend!!.id!!.equals(curruser)
                    if(me){
                        viewHolder.profmvright!!.visibility=View.VISIBLE
                        viewHolder.profimv!!.visibility=View.GONE
                        viewHolder.mtextv!!.gravity=(Gravity.CENTER_VERTICAL or Gravity.RIGHT)
                        viewHolder.sengertv!!.gravity=(Gravity.CENTER_VERTICAL or Gravity.RIGHT)

                        mdata!!.child("Users").child(curruser)
                                .addValueEventListener(object:ValueEventListener{
                                    override fun onDataChange(data: DataSnapshot?) {
                                        var imurl=data!!.child("thumb_image").value.toString()
                                        var dispname=data!!.child("Display_name").value.toString()
                                        viewHolder.sengertv!!.text="I Wrote.."
                                        Picasso.with(viewHolder.profimv!!.context)
                                                .load(imurl)
                                                .placeholder(R.drawable.profile).
                                                into(viewHolder.profmvright)
                                    }

                                    override fun onCancelled(p0: DatabaseError?) {

                                    }
                                })
                    }else{
                        viewHolder.profmvright!!.visibility=View.GONE
                        viewHolder.profimv!!.visibility=View.VISIBLE
                        viewHolder.mtextv!!.gravity=(Gravity.CENTER_VERTICAL or Gravity.LEFT)
                        viewHolder.sengertv!!.gravity=(Gravity.CENTER_VERTICAL or Gravity.LEFT)

                        mdata!!.child("Users").child(userid)
                                .addValueEventListener(object:ValueEventListener{
                                    override fun onDataChange(data: DataSnapshot?) {
                                        var imurl=data!!.child("thumb_image").value.toString()
                                        var dispname=data!!.child("Display_name").value.toString()
                                        viewHolder.sengertv!!.text="$dispname Wrote.."
                                        Picasso.with(viewHolder.profimv!!.context)
                                                .load(imurl)
                                                .placeholder(R.drawable.profile).
                                                into(viewHolder.profimv)
                                    }

                                    override fun onCancelled(p0: DatabaseError?) {

                                    }
                                })

                    }
                }
            }
        }
        mrecv.layoutManager=lmanage
        mrecv.adapter=mfadap
        send.setOnClickListener{
            if(!intent.extras.get("name").toString().equals("")){
                var cuser=intent.extras.get("name")
                var cid=fuser!!.uid

                var frmessage=Friendm(cid,msgedit.text.toString().trim(),cuser.toString().trim())
                mdata!!.child("messages").push().setValue(frmessage)
                msgedit.setText("")
            }
        }

    }

    class Messagehold(itemView:View):RecyclerView.ViewHolder(itemView){
        var mtextv:TextView?=null
        var sengertv:TextView?=null
        var profimv:CircleImageView?=null
        var profmvright:CircleImageView?=null

        fun bindView(fmessage:Friendm){

            mtextv=itemView.findViewById(R.id.mtext)
            sengertv=itemView.findViewById(R.id.mtext2)
            profimv=itemView.findViewById(R.id.mimview)
            profmvright=itemView.findViewById(R.id.mimviewright)
            sengertv!!.text=fmessage.name
            mtextv!!.text=fmessage.text
        }
    }
}
