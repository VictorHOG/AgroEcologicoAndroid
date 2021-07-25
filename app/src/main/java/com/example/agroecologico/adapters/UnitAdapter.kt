package com.example.agroecologico.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.agroecologico.R
import com.example.agroecologico.data.UnitModel
import com.example.agroecologico.databinding.ItemUnitBinding

import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions

class UnitAdapter(options: FirestoreRecyclerOptions<UnitModel>):
    FirestoreRecyclerAdapter<UnitModel, UnitAdapter.UnitViewHolder>(options) {

    inner class UnitViewHolder(itemView:View) : RecyclerView.ViewHolder(itemView) {
        val viewBinding = ItemUnitBinding.bind(itemView)

        val unitItemName: TextView = viewBinding.UnitItemName
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UnitViewHolder {
        return UnitViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_unit, parent, false))
    }

    override fun onBindViewHolder(holder: UnitViewHolder, position: Int, model: UnitModel) {
        holder.unitItemName.text = model.name
    }
}