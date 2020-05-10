package com.example.greenwaste2.ui.dashboard

import android.app.Activity
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.example.greenwaste2.R
import com.example.greenwaste2.databinding.ActivityAddEwasteBinding
import com.example.greenwaste2.model.Ewaste
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Runnable
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.File

class AddEwaste : AppCompatActivity() {
    lateinit var binding:ActivityAddEwasteBinding
    private lateinit var storagereference: StorageReference
    private lateinit var   databasereference: DatabaseReference
    lateinit var files: File
    lateinit var fileuris:Uri
    private var image:String?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.setContentView(this,R.layout.activity_add_ewaste)
        storagereference = FirebaseStorage.getInstance().getReference("Items Upload").child("${System.currentTimeMillis()}")
        databasereference = FirebaseDatabase.getInstance().getReference("Items Upload")
        selectAndConvertImage()
        Validate()
    }


    private fun Validate() {
        binding.addEwaste.setOnClickListener {
            if (binding.itemName.text.isNullOrEmpty()) {
                binding.itemName.error = "Name cant be empty"
                return@setOnClickListener
            }
            if (binding.Desc.text.isNullOrEmpty()){
                binding.Desc.error = "cant be empty"
                return@setOnClickListener
            }
            if (binding.addressl.text.isNullOrEmpty())
            {
                binding.addressl.error = "Address cant be empty"
                return@setOnClickListener
            }
            if (image.isNullOrEmpty()){
                showToast("pick image")
                return@setOnClickListener
            }
            upload(fileuris,files)

        }


    }

    private fun selectAndConvertImage(){

        binding.imageiew.setOnClickListener {
            image="hello"
            ImagePicker.with(this)
                    .crop()
                    .compress(1024)
                    .maxResultSize(1080, 1080)
                    .start { resultCode, data ->
                        when (resultCode) {
                            Activity.RESULT_OK -> {
                                //Image Uri will not be null for RESULT_OK
                                val fileUri = data?.data
                                Glide.with(this).load(fileUri).into(binding.imageiew)

                                //You can get File object from intent
                                val file: File? = ImagePicker.getFile(data)

                                //You can also get File Path from intent
                                val filePath: String? = ImagePicker.getFilePath(data)
                                files=file!!
                                fileuris=fileUri!!

                            }
                            ImagePicker.RESULT_ERROR -> {
                                showToast(ImagePicker.getError(data))
                            }
                            else -> {
                                showToast("Task canceled")
                            }
                        }
                    }
        }


    }

    private fun showToast(message:String){
        Toast.makeText(this,message,Toast.LENGTH_LONG).show()
    }

    private fun  upload(file: Uri,fileE: File){
        Log.d("debug","hit here")
        val storageReference=storagereference
        storageReference.apply {
            startLoader(0)
            Log.d("debug","hit here")
            putFile(file).addOnSuccessListener {
                it.storage.downloadUrl.addOnCompleteListener {
                    val item=Ewaste(it.result.toString(),binding.itemName.text.toString(),binding.Desc.text.toString(),binding.addressl.text.toString())
                    val itemid=databasereference.push().key
                    databasereference.child(itemid!!).setValue(item)
                }

                finish()

            }.addOnFailureListener {
                startLoader(1)
                showToast(it.message.toString())
            }
        }

    }

    //0 shows loading state ,1 shows default state
    fun startLoader(status: Int) {
        when (status) {
            0 -> {
                binding.addEwaste.text = "Loading"
                binding.addEwaste.isClickable = false
                binding.addEwaste.alpha = 0.6f
                binding.progressbar.visibility = View.VISIBLE
            }
            1 -> {
                binding.addEwaste.text = "Add Ewaste"
                binding.addEwaste.alpha = 1.0f
                binding.addEwaste.isClickable = true
                binding.progressbar.visibility = View.GONE
            }
        }
    }
}
