package com.example.greenwaste2.model

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Ewaste(
        val iimage: String? = null,
        val iname: String? = null,
        val idecription: String? = null,
        val iaddress: String? = null
)
