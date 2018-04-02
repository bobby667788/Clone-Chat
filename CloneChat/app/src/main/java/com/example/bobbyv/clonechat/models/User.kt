package com.example.bobbyv.clonechat.models

/**
 * Created by Bobby on 22-02-2018.
 */
class User() {
    var Display_name:String?=null
    var image:String?=null
    var thumb_image:String?=null
    var Status:String?=null

    constructor(displayn:String,image:String,thumb:String,userstat:String):this(){
        this.Display_name=displayn
        this.image=image
        this.thumb_image=thumb
        this.Status=userstat
    }
}