package com.example.greenwaste2.ui.dashboard

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.greenwaste2.R
import com.example.greenwaste2.databinding.FragmentProfileBinding
import com.example.greenwaste2.model.Ewaste
import com.example.greenwaste2.model.RegisterList
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class Profile : Fragment() {
    lateinit var binding:FragmentProfileBinding
    private lateinit var database: DatabaseReference
    private lateinit var auth:FirebaseAuth


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding=DataBindingUtil.inflate(inflater,R.layout.fragment_profile, container, false)
        setHasOptionsMenu(true)
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().getReference("RegisterList")
        setUpTextFeild()
        return binding.root
    }

    private fun setUpTextFeild() {
        database.addValueEventListener(object :ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                p0.children.forEach {
                    if (it.child("email").value==auth.currentUser?.email){
                        Log.d("tagger","${it.value}")
                        val user: RegisterList? =it.getValue(RegisterList::class.java)
                        binding.apply {
                            firstname.setText(user?.firstname)
                            lastname.setText(user?.lastname)
                            email.setText(user?.email)
                            Phonenumber.setText(user?.password)
                        }

                    }

                }
            }

        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.logout->{
              activity?.finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.profile_menu,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

}
