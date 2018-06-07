package com.example.sinb.cnn10reader

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.recyclerview_card.view.*
import kotlinx.android.synthetic.main.recyclerview_item.view.*

class RecyclerViewAdapter(val items : ArrayList<String>, val context : Context) : RecyclerView.Adapter<ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.recyclerview_card, parent, false))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.img.setImageResource(R.drawable.cnn10logo)
        holder.itemContentView?.text = items[position]
        holder.bind(context, items[position])
    }
}

class ViewHolder(view : View) : RecyclerView.ViewHolder(view) {
    val img = view.img_row
    val itemContentView = view.card_row
    val layoutHolder = view.findViewById<CardView>(R.id.card_layout_holder)

    fun bind(context: Context, item: String) {
        layoutHolder.setOnClickListener {
            //Toast.makeText(context, item, Toast.LENGTH_SHORT).show()
            context.startActivity(Intent(context, ViewActivity::class.java).putExtra("date", item))
        }
    }
}





