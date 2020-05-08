package com.example.greenwaste2.ui.dashboard

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.afollestad.recyclical.datasource.dataSourceTypedOf
import com.afollestad.recyclical.setup
import com.afollestad.recyclical.withItem
import com.bumptech.glide.Glide
import com.example.greenwaste2.R
import com.example.greenwaste2.databinding.FragmentAdViewBinding
import com.example.greenwaste2.model.AdViewModel
import com.example.greenwaste2.utils.AdsViewHolder


class AdView : Fragment() {
    lateinit var binding:FragmentAdViewBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
binding=DataBindingUtil.inflate(inflater,R.layout.fragment_ad_view, container, false)
        setupRecyclerView()
        return binding.root
    }

    private fun setupRecyclerView(){
        val datasource = dataSourceTypedOf(
                AdViewModel("Iphone X", "₦200,000",  R.drawable.adview1),
                AdViewModel("Macbook pro", "₦300,000", R.drawable.adview2),
                AdViewModel("Pixels 4", "₦550,000",  R.drawable.adview3),
                AdViewModel("Imac", "₦100,000",  R.drawable.adview4),
                AdViewModel("Iphone X", "₦200,000",  R.drawable.adviw5)
        )

        binding.list.setup {
            withDataSource(datasource)
            withItem<AdViewModel, AdsViewHolder>(R.layout.ads_item){
                onBind(::AdsViewHolder){ index, item ->
                    adsName.text=item.adsText
                    adsDescription.text=item.adsDescrption
                    Glide.with(this@AdView).load(item.image).into(image)
                }
                onClick {

                }
            }
        }
    }

}
