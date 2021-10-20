package com.example.agroecologico.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Product(var stall:String = "",
                   var name:String = "",
                   var urlImage:String = "",
                   var price:Map<String, String> = mutableMapOf()): Parcelable