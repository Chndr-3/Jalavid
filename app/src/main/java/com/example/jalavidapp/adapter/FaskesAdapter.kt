package com.example.jalavidapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.jalavidapp.R
import com.example.jalavidapp.model.DataItemFaskes

class FaskesAdapter(private val listFaskes: List<DataItemFaskes>) : RecyclerView.Adapter<FaskesAdapter.ViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int) =
        ViewHolder(
            LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.item_rs_faskes, viewGroup, false)
        )

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val risk = listFaskes[position]
        viewHolder.kota.text = risk.nama
        viewHolder.score.text = risk.alamat
        viewHolder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(listFaskes[viewHolder.adapterPosition]) }
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val kota: TextView = view.findViewById(R.id.name)
        val score: TextView = view.findViewById(R.id.address)
    }

    override fun getItemCount() = listFaskes.size
    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }
    interface OnItemClickCallback {
        fun onItemClicked(data: DataItemFaskes)
    }
}