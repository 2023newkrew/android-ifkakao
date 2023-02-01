package com.example.ifkakao.presentation.main_session.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ifkakao.R

class TrackGridAdapter(var list: MutableList<Pair<Int,String>>): RecyclerView.Adapter<TrackGridAdapter.GridAdapter>() {

    class GridAdapter(layout: View): RecyclerView.ViewHolder(layout)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GridAdapter {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.track_item, parent, false)

        return GridAdapter(view)
    }

    override fun onBindViewHolder(holder: GridAdapter, position: Int) {
        val image = holder.itemView.findViewById<ImageView>(R.id.track_item_image)
        image.setImageResource(list[position].first)
        val text = holder.itemView.findViewById<TextView>(R.id.track_item_text)
        text.text = list[position].second
    }

    override fun getItemCount(): Int {
        return list.size
    }
}