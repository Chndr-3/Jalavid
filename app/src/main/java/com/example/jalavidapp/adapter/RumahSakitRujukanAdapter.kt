package com.example.jalavidapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.jalavidapp.R
import com.example.jalavidapp.model.RumahSakitItem

class RumahSakitRujukanAdapter(private val listRS: List<RumahSakitItem>) : RecyclerView.Adapter<RumahSakitRujukanAdapter.ViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int) =
        ViewHolder(
            LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.item_rs_faskes, viewGroup, false)
        )

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val rs = listRS[position]
        viewHolder.nama.text = rs.nama
        viewHolder.alamat.text = rs.alamat
        viewHolder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(listRS[viewHolder.adapterPosition]) }
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nama: TextView = view.findViewById(R.id.name)
        val alamat: TextView = view.findViewById(R.id.address)
    }

    override fun getItemCount() = listRS.size
    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }
    interface OnItemClickCallback {
        fun onItemClicked(data: RumahSakitItem)
    }
}