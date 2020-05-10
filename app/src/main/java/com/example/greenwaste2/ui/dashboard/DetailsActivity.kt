package com.example.greenwaste2.ui.dashboard

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.example.greenwaste2.R
import com.example.greenwaste2.databinding.ActivityDetailsBinding
import com.example.greenwaste2.model.Ewaste
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class DetailsActivity : AppCompatActivity() {
    private lateinit var database: DatabaseReference
    private lateinit var auth:FirebaseAuth
    lateinit var binding:ActivityDetailsBinding
    lateinit var value:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.setContentView(this,R.layout.activity_details)
        value= intent.extras!![USER_ID] as String
        auth=FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().getReference(auth.currentUser?.uid.toString())
        setUpView()
        Log.d("userID", value)
    }

    private fun setUpView() {
        binding.toolbar.setNavigationOnClickListener { finish() }
        val getData  = object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                Log.d("tagger",p0.message)
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()){

                    dataSnapshot.children.forEach {
                        Log.d("tagger","${it.child("iname").value}")
                        if(it.child("iname").value == value){
                            val eMessage: Ewaste? =it.getValue(Ewaste::class.java)
                            Glide.with(this@DetailsActivity).load(eMessage?.iimage).into(binding.imageview)
                            binding.apply {
                                name.text=eMessage?.iname
                                description.text=eMessage?.idecription
                            }
                        }
                    }




                }
            }

        }
        database.addValueEventListener(getData)
    }
    companion object{
         var USER_ID="ggg"
    }
}
