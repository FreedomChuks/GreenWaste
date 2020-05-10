package com.example.greenwaste2.ui.dashboard

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.greenwaste2.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class DetailsActivity : AppCompatActivity() {
    private lateinit var database: DatabaseReference
    private lateinit var auth:FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        auth=FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().getReference("Items Upload").child(auth.currentUser?.uid.toString())
        setUpView()
    }

    private fun setUpView() {

    }

    companion object{
         var USER_ID="data"
    }
}
