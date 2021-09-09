package com.example.agroecologico.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.agroecologico.R
import com.example.agroecologico.data.ImageStall
import com.example.agroecologico.databinding.ItemMyStallBinding
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions

class ImageStallAdapter(options: FirestoreRecyclerOptions<ImageStall>) :
    FirestoreRecyclerAdapter<ImageStall, ImageStallAdapter.ImageStallViewHolder>(options) {

    private lateinit var mContext: Context

    inner class ImageStallViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val viewBinding = ItemMyStallBinding.bind(itemView)

        val myStallItemDelete: ImageButton = viewBinding.imageButtonDelete
        val myStallItemImage: ImageView = viewBinding.imageViewStall

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageStallViewHolder {
        mContext = parent.context
        return ImageStallViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_my_stall, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ImageStallViewHolder, position: Int, model: ImageStall) {

        Glide.with(mContext)
            .load(model.urlImage)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .centerCrop()
            .into(holder.myStallItemImage)

    }


}