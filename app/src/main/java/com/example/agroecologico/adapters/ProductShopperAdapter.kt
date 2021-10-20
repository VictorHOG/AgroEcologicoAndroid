package com.example.agroecologico.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.agroecologico.ProductDetailActivity
import com.example.agroecologico.R
import com.example.agroecologico.data.Product
import com.example.agroecologico.databinding.ItemProductShopperBinding
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import java.io.Serializable

class ProductShopperAdapter(options: FirestoreRecyclerOptions<Product>) :
    FirestoreRecyclerAdapter<Product, ProductShopperAdapter.ProductShopperAdapterViewHolder>(options) {

    private lateinit var mContext: Context


    inner class ProductShopperAdapterViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        val viewBinding = ItemProductShopperBinding.bind(itemView)

        val nameProduct: TextView = viewBinding.textViewNameProduct
        val priceProduct: TextView = viewBinding.textViewPriceProduct
        val imageProduct: ImageView = viewBinding.imageViewProduct
        val buttonAdd:Button = viewBinding.buttonAddProduct
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProductShopperAdapterViewHolder {
        mContext = parent.context
        return  ProductShopperAdapterViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_product_shopper, parent, false)
        )
    }

    override fun onBindViewHolder(
        holder: ProductShopperAdapterViewHolder,
        position: Int,
        model: Product
    ) {
       holder.nameProduct.text = model.name

        var cadena:String = ""

        for (key in model.price.keys) {
            cadena+= key + "   " + model.price[key] + "\n"
        }

        holder.priceProduct.text = cadena

        Glide.with(mContext)
            .load(model.urlImage)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .centerCrop()
            .into(holder.imageProduct)

        holder.buttonAdd.setOnClickListener {
            Toast.makeText(mContext, "se selecciono el item", Toast.LENGTH_SHORT).show()
            val intentDetail = Intent(mContext,ProductDetailActivity::class.java)
            intentDetail.putExtra("detail",model)
            mContext.startActivity(intentDetail)
        }
    }
}