package com.example.jalavidapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.jalavidapp.R
import com.example.jalavidapp.model.TanyaJawab

class TanyaJawabAdapter(private val listTanyaJawab: List<TanyaJawab>) : RecyclerView.Adapter<TanyaJawabAdapter.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int) =
        ViewHolder(
            LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.item_tanya_jawab, viewGroup, false)
        )

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val tanyaJawab = listTanyaJawab[position]
        viewHolder.question.text = tanyaJawab.question
        viewHolder.answer.text = tanyaJawab.answer
        val isVisible: Boolean = tanyaJawab.isVisible
        viewHolder.answer.visibility = if(isVisible) View.VISIBLE else View.GONE
        viewHolder.card.setOnClickListener{
            tanyaJawab.isVisible = !tanyaJawab.isVisible
            notifyItemChanged(position)
        }
        if(isVisible) {
            viewHolder.answerLabel.visibility = View.VISIBLE
            viewHolder.dropDown.setImageResource(R.drawable.ic_baseline_arrow_drop_up_24)
        }else{
            viewHolder.answerLabel.visibility = View.GONE
            viewHolder.dropDown.setImageResource(R.drawable.ic_baseline_arrow_drop_down_24)
        }

    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val question: TextView = view.findViewById(R.id.question)
       val answer: TextView = view.findViewById(R.id.answer)
        val card: CardView = view.findViewById(R.id.card_view)
        val answerLabel : TextView = view.findViewById(R.id.answerLabel)
        val dropDown: ImageView = view.findViewById(R.id.dropDown)
    }

    override fun getItemCount() = listTanyaJawab.size


}