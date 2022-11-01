package com.example.jalavidapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.jalavidapp.R
import com.example.jalavidapp.model.Cegah

class CegahAdapter(private val listCegah: List<Cegah>) : RecyclerView.Adapter<CegahAdapter.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int) =
        ViewHolder(
            LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.cegah_item, viewGroup, false)
        )

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val cegah = listCegah[position]
        viewHolder.prevention.text = cegah.prevention
        viewHolder.preventionDescription.text = cegah.preventionDescription
        viewHolder.img.setImageResource(cegah.img)
        val isVisible: Boolean = cegah.isVisible
        viewHolder.preventionDescription.visibility = if(isVisible) View.VISIBLE else View.GONE
        viewHolder.card.setOnClickListener{
            cegah.isVisible = !cegah.isVisible
            notifyItemChanged(position)
        }
        if(isVisible) {
            viewHolder.dropDown.setImageResource(R.drawable.ic_baseline_arrow_drop_up_24)
        }else{
            viewHolder.dropDown.setImageResource(R.drawable.ic_baseline_arrow_drop_down_24)
        }

    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val img: ImageView = view.findViewById(R.id.preventionImg)
        val prevention: TextView = view.findViewById(R.id.prevention)
        val preventionDescription: TextView = view.findViewById(R.id.preventionDescription)
        val card: CardView = view.findViewById(R.id.card)
        val dropDown: ImageView = view.findViewById(R.id.dropDown)
    }

    override fun getItemCount() = listCegah.size


}