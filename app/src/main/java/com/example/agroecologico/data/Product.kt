package com.example.agroecologico.data

data class Product(var stall:String = "",
                   var name:String = "",
                   var urlImage:String = "",
                   var price:Map<String, String> = mutableMapOf())