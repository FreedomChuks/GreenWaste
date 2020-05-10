package com.example.greenwaste2.model

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class RegisterList (
        val firstname:String?=null,
        val  password:String?=null,
        val  gender:String?=null,
        val  phonenumber:String?=null,
        val  email:String?=null,
        val lastname:String?=null
)

