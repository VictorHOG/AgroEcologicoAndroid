package com.example.agroecologico.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ShoppingCart (var currentDate:String = "",
                         var shopperUser:String = "",
                         var productImage:String = "",
                         var productName:String = "",
                         var productPrice:String = "",
                         var productUnit:String = "",
                         var totalPrice:String = "",
                         var totalQuantity:String = "",): Parcelable