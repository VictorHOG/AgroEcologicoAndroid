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
import com.example.agroecologico.data.Worker
import com.example.agroecologico.databinding.ItemVendorBinding
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions

class WorkerAdapter(options: FirestoreRecyclerOptions<Worker>) :
    FirestoreRecyclerAdapter<Worker, WorkerAdapter.WorkerViewHolder>(
        options) {

    private lateinit var mContext: Context

    inner class WorkerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val viewBinding = ItemVendorBinding.bind(itemView)

        val vendorName: TextView = viewBinding.textViewNameVendor
        val imageVendor: ImageView = viewBinding.imageViewVendor
        val overflow: ImageView = viewBinding.imageViewOptionsVendor
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkerViewHolder {
        mContext = parent.context
        return WorkerViewHolder( LayoutInflater.from(parent.context).inflate(R.layout.item_vendor, parent, false))
    }

    override fun onBindViewHolder(holder: WorkerViewHolder, position: Int, model: Worker) {

        holder.vendorName.text = model.name

        Glide.with(mContext)
            .load(model.urlImage)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .centerCrop()
            .into(holder.imageVendor)

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
                    Toast.makeText(mContext, "Editar Item worker", Toast.LENGTH_SHORT).show()
                }
                R.id.optionsDelete -> {
                    Toast.makeText(mContext, "Eliminar Item worker", Toast.LENGTH_SHORT).show()
                }
            }
            true
        }
        popup.show()
    }
}