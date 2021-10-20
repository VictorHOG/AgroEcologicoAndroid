package com.example.agroecologico.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.agroecologico.R
import com.example.agroecologico.data.Product
import com.example.agroecologico.databinding.ItemProductBinding
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions

class ProductVendorAdapter(options: FirestoreRecyclerOptions<Product>) :
    FirestoreRecyclerAdapter<Product, ProductVendorAdapter.ProductVendorAdapterViewHolder>(options) {

    private lateinit var mContext: Context

    inner class ProductVendorAdapterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val viewBinding = ItemProductBinding.bind(itemView)

        val nameProduct: TextView = viewBinding.textViewNameProduct
        val priceProduct: TextView = viewBinding.textViewPriceProduct
        val imageProduct: ImageView = viewBinding.imageViewProduct
        val overflow: ImageView = viewBinding.imageViewOptionsVendor
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProductVendorAdapterViewHolder {
        mContext = parent.context
        return ProductVendorAdapterViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false)
        )
    }

    override fun onBindViewHolder(
        holder: ProductVendorAdapterViewHolder,
        position: Int,
        model: Product
    ) {
        holder.nameProduct.text = model.name

        holder.priceProduct.text = model.price.toString()

        /*var textView:String = ""
        val listPrice = model.price
        val count = listPrice.size

        for (i in 0..(count-1)) {
            textView += listPrice[i]
        }*/
        /*for (i in 0 until model.price.size) {
            holder.priceProduct.append(model.price[i].toString())
        }*/

        Glide.with(mContext)
            .load(model.urlImage)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .centerCrop()
            .into(holder.imageProduct)

        holder.overflow.setOnClickListener {
            showPopupMenu(holder.overflow)
        }

    }

    private fun showPopupMenu(overflow: ImageView) {
        var popup: PopupMenu = PopupMenu(mContext, overflow)
        var inflater: MenuInflater = popup.getMenuInflater()
        inflater.inflate(R.menu.menu_options_vendor, popup.getMenu())
        popup.setOnMenuItemClickListener {
            when(it.itemId) {
                R.id.optionsEdit -> {
                    Toast.makeText(mContext, "Editar Item", Toast.LENGTH_SHORT).show()
                }
                R.id.optionsDelete -> {
                    Toast.makeText(mContext, "Eliminar Item", Toast.LENGTH_SHORT).show()
                }
            }
            true
        }
        popup.show()
    }

}

