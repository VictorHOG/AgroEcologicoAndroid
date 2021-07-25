package com.example.agroecologico.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.agroecologico.R
import com.example.agroecologico.data.Stall
import com.example.agroecologico.databinding.ItemStallBinding
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions

class StallAdapter(options: FirestoreRecyclerOptions<Stall>) :
    FirestoreRecyclerAdapter<Stall, StallAdapter.StallViewHolder>(options) {

    inner class  StallViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val viewBinding = ItemStallBinding.bind(itemView)


        val stallItemName: TextView = viewBinding.stallItemName
        val stallItemDescription: TextView = viewBinding.stallItemDescription
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StallViewHolder {
       return StallViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_stall, parent, false))
    }

    override fun onBindViewHolder(holder: StallViewHolder, position: Int, model: Stall) {
        holder.stallItemName.text = model.stallName
        holder.stallItemDescription.text = model.userID
    }
}