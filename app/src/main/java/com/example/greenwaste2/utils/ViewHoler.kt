package com.example.greenwaste2.utils

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.afollestad.recyclical.ViewHolder
import com.example.greenwaste2.R

class EwasteViewHolder(itemview: View):ViewHolder(itemview){
    val name: TextView =itemview.findViewById(R.id.itemName)
    val description: TextView =itemview.findViewById(R.id.itemdescription)
    val image:ImageView=itemview.findViewById(R.id.circleImageView)

}

class AdsViewHolder(itemview: View):ViewHolder(itemview){
    val adsName: TextView =itemview.findViewById(R.id.adsName)
    val adsDescription: TextView =itemview.findViewById(R.id.adsdescription)
    val image:ImageView=itemview.findViewById(R.id.adsImageView)
}