package com.example.greenwaste2.ui.dashboard;

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.afollestad.recyclical.datasource.DataSource
import com.afollestad.recyclical.datasource.dataSourceTypedOf
import com.afollestad.recyclical.setup
import com.afollestad.recyclical.withItem
import com.bumptech.glide.Glide
import com.example.greenwaste2.R
import com.example.greenwaste2.databinding.FragmentHomeBinding
import com.example.greenwaste2.model.Ewaste
import com.example.greenwaste2.ui.dashboard.DetailsActivity.Companion.USER_ID
import com.example.greenwaste2.utils.EwasteViewHolder
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_add_ewaste.*
import java.util.*

class Home : Fragment() {
    lateinit var binding: FragmentHomeBinding
    private lateinit var database: DatabaseReference
    private lateinit var auth: FirebaseAuth


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding=DataBindingUtil.inflate(inflater,R.layout.fragment_home, container, false)
        setUpNavigation()
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().getReference("Items Upload").child(auth.currentUser?.uid.toString())
        getData()
        return binding.root
    }

    private fun getData(){
        val getData  = object :ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
                Log.d("tagger",p0.message)
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val productList=ArrayList<Ewaste>()
                val eMessage: Ewaste? =dataSnapshot.getValue(Ewaste::class.java)
                productList.add(eMessage!!)
                Log.d("tagger","$productList")

                Log.d("tagger"," after list $productList")
                val dataSource= dataSourceTypedOf(productList)
                setUpRecylerView(dataSource)
                productList.clear()
            }

        }
        database.addValueEventListener(getData)
        // Keep copy of post listener so we can remove it when app stops
    }

    private fun setUpRecylerView(dataSource: DataSource<Ewaste>) {
        binding.list.setup {
            withDataSource(dataSource)
            withItem<Ewaste, EwasteViewHolder>(R.layout.ewaste_item){
                onBind(::EwasteViewHolder){index, item ->  
                    name.text=item.iname
                    description.text=item.idecription
                    Glide.with(this@Home).load(item.iimage).into(image)
                }
                onClick {
                    val intent=Intent(activity,DetailsActivity::class.java)
                    intent.putExtra(USER_ID,item.iname)
                    startActivity(intent)
                }
            }
        }
    }

    private fun setUpNavigation() {
        binding.add.setOnClickListener {
            startActivity(Intent(context,AddEwaste::class.java))
        }
    }

}
