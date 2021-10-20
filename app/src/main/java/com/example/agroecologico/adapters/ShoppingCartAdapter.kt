package com.example.agroecologico.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.agroecologico.R
import com.example.agroecologico.data.ShoppingCart
import com.example.agroecologico.databinding.ItemCartBinding
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions

class ShoppingCartAdapter(options: FirestoreRecyclerOptions<ShoppingCart>) :
    FirestoreRecyclerAdapter<ShoppingCart, ShoppingCartAdapter.ShoppingCartAdapterViewHolder>(
        options
    ) {

    private lateinit var mContext: Context

    private  var totalPrice:Int = 0


    inner class ShoppingCartAdapterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val viewBinding = ItemCartBinding.bind(itemView)

        val productImage:ImageView = viewBinding.imageViewItemProduct
        var productName:TextView = viewBinding.itemProductName
        var productPrice:TextView = viewBinding.itemProductPrice
        var totalPrice:TextView = viewBinding.ItemProductTotal
        var totalQuantity:TextView = viewBinding.itemProductUnits
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ShoppingCartAdapterViewHolder {
        mContext = parent.context
        return  ShoppingCartAdapterViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_cart, parent, false))
    }

    override fun onBindViewHolder(
        holder: ShoppingCartAdapterViewHolder,
        position: Int,
        model: ShoppingCart
    ) {

        Glide.with(mContext)
            .load(model.productImage)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .centerCrop()
            .into(holder.productImage)

        holder.productName.text = model.productName
        holder.totalQuantity.text = model.totalQuantity
        holder.productPrice.text = model.productPrice
        //holder.productPrice.text = model.productPrice + " precio por " + model.productUnit

        holder.totalPrice.text = model.totalPrice

        // Pass total amount to Fragment Shopper Buy
        totalPrice = totalPrice + model.totalPrice.toInt()
        var intent = Intent("MyTotalAmount")
        intent.putExtra("totalAmount", totalPrice)
        LocalBroadcastManager.getInstance(mContext).sendBroadcast(intent)


    }
}