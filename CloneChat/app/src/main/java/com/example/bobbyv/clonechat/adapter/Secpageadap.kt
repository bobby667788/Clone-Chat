package com.example.bobbyv.clonechat.adapter

import android.support.v4.app.FragmentManager
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import com.example.bobbyv.clonechat.fragments.Chatsfragment
import com.example.bobbyv.clonechat.fragments.Userfragment

/**
 * Created by Bobby on 18-02-2018.
 */
class Secpageadap(fm:FragmentManager):FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        when (position) {
            0 -> {
                return Userfragment()
            }
            1 -> {
                return Chatsfragment()
            }
        }
        return null!!
    }
    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence {
        when(position){
            0->return "USERS"
            1->return "CHATS"
        }
        return null!!
    }
}