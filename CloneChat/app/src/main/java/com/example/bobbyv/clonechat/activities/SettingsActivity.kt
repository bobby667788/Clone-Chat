package com.example.bobbyv.clonechat.activities

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.bobbyv.clonechat.R
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import com.squareup.picasso.Picasso
import com.theartofdev.edmodo.cropper.CropImage
import id.zelory.compressor.Compressor
import kotlinx.android.synthetic.main.activity_settings.*
import java.io.ByteArrayOutputStream
import java.io.File

class SettingsActivity : AppCompatActivity() {

    var mdata:DatabaseReference?=null
    var curruser:FirebaseUser?=null
    var storageref:StorageReference?=null
    var Galid:Int=1

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        curruser=FirebaseAuth.getInstance().currentUser
        var userid=curruser!!.uid
        mdata=FirebaseDatabase.getInstance().reference.child("Users").child(userid)
        storageref=FirebaseStorage.getInstance().reference
        mdata!!.addValueEventListener(object:ValueEventListener{

            override fun onDataChange(datasnap: DataSnapshot?) {
               var dispname=datasnap!!.child("Display_name").value
                var image=datasnap!!.child("image").value.toString()
                var status=datasnap!!.child("Status").value
                var thumb=datasnap!!.child("thumb_image").value
                textView2.text=dispname.toString()
                textView3.text=status.toString()
                if(!image!!.equals("default")){
                    Picasso.with(applicationContext)
                            .load(image).placeholder(R.drawable.profile).into(settingid)


                }
            }

            override fun onCancelled(p0: DatabaseError?) {

            }
        })
        changestatusid.setOnClickListener{
            var intent=Intent(this,StatusActivity::class.java)
            intent.putExtra("Status",textView3.text.toString().trim())
            startActivity(intent)
        }
        changepicid.setOnClickListener{
            var gallery=Intent()
            gallery.type="image/*"
            gallery.action=Intent.ACTION_GET_CONTENT
            startActivityForResult(Intent.createChooser(gallery,"SELECT IMAGE"),Galid)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if(requestCode==Galid && resultCode==Activity.RESULT_OK){
            var image:Uri=data!!.data
            CropImage.activity(image).setAspectRatio(1,1).start(this)

        }
        if(requestCode===CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
            val result=CropImage.getActivityResult(data)
            if(resultCode===Activity.RESULT_OK){
                val resulturi=result.uri
                var thumbfile=File(resulturi.path)
                var thumbbit=Compressor(this)
                        .setMaxHeight(200).setMaxHeight(200).setQuality(65).compressToBitmap(thumbfile)

                var bytarray=ByteArrayOutputStream()
                thumbbit.compress(Bitmap.CompressFormat.JPEG,100,bytarray)
                var thumbbitarr:ByteArray
                thumbbitarr=bytarray.toByteArray()


                curruser=FirebaseAuth.getInstance().currentUser
                var userid=curruser!!.uid
                var fpath=storageref!!.child("images chat").child(userid+".jpg")
                var thumbfpath=storageref!!.child("images chat")
                        .child("thumb").child(userid+".jpg")

                fpath.putFile(resulturi).addOnCompleteListener{
                    task: Task<UploadTask.TaskSnapshot> ->
                    Toast.makeText(this,"Saving!", Toast.LENGTH_LONG).show()
                    if(task.isSuccessful){
                        var downurl=task.result.downloadUrl.toString()
                        var upload:UploadTask=thumbfpath.putBytes(thumbbitarr)
                        upload.addOnCompleteListener{
                            task:Task<UploadTask.TaskSnapshot> ->
                            var uploadurl=task.result.downloadUrl.toString()
                            if(task.isSuccessful){
                                var updateobj=HashMap<String,Any>()
                                updateobj.put("image",downurl)
                                updateobj.put("thumb_image",uploadurl)
                                this.mdata!!.updateChildren(updateobj).addOnCompleteListener{
                                    task: Task<Void> ->
                                    if(task.isSuccessful){
                                        Toast.makeText(this,"Image is Saved", Toast.LENGTH_LONG).show()
                                    }
                                    else{
                                        Toast.makeText(this,"Not Saved Try Again", Toast.LENGTH_LONG).show()
                                    }
                                }
                            }
                        }

                    }
                }
            }
        }
       // super.onActivityResult(requestCode, resultCode, data)
    }
}
