package com.example.bobbyv.clonechat.fragments


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout

import com.example.bobbyv.clonechat.R
import com.example.bobbyv.clonechat.adapter.Useradapt
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.fragment_userfragment.*


/**
 * A simple [Fragment] subclass.
 */
class Userfragment : Fragment() {

    var udt:DatabaseReference?=null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_userfragment, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var linlayman=LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
        var udt=FirebaseDatabase.getInstance().reference.child("Users")
        recycleid.setHasFixedSize(true)
        recycleid.layoutManager=linlayman
        recycleid.adapter=Useradapt(udt,context)
    }
}// Required empty public constructor
