package com.example.agroecologico.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.agroecologico.MainActivity
import com.example.agroecologico.R
import com.example.agroecologico.StallDetailActivity
import com.example.agroecologico.data.Stall
import com.example.agroecologico.databinding.ItemCardStallBinding
import com.example.agroecologico.databinding.ItemStallBinding
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions

class StallAdapter(options: FirestoreRecyclerOptions<Stall>) :
    FirestoreRecyclerAdapter<Stall, StallAdapter.StallViewHolder>(options) {

    private lateinit var mContext: Context

    inner class StallViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val viewBinding = ItemCardStallBinding.bind(itemView)

        val stallItem: LinearLayout = viewBinding.linearLayoutProductList
        val stallItemName: TextView = viewBinding.stallItemName
        val stallItemDescription: TextView = viewBinding.stallItemDescription
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StallViewHolder {
        mContext = parent.context
        return StallViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_card_stall, parent, false)
        )
    }

    override fun onBindViewHolder(holder: StallViewHolder, position: Int, model: Stall) {
        holder.stallItemName.text = model.stallName
        holder.stallItemDescription.text = model.userID

        holder.stallItem.setOnClickListener {
            val intentDetail = Intent(mContext, StallDetailActivity::class.java).apply {
                putExtra("name", model.stallName)
                putExtra("email", model.email)
            }
            mContext.startActivity(intentDetail)
        }
    }
}