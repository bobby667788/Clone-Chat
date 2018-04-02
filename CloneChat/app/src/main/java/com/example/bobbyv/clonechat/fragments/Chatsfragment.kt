package com.example.bobbyv.clonechat.fragments


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.bobbyv.clonechat.R


/**
 * A simple [Fragment] subclass.
 */
class Chatsfragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_chatsfragment, container, false)
    }


        //override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                                 // savedInstanceState: Bundle): View? {
            // Inflate the layout for this fragment
            //return inflater.inflate(R.layout.fragment_chatsfragment, container, false)
       // }

}// Required empty public constructor
