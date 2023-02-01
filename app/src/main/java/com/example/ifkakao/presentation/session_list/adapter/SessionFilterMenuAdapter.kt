package com.example.ifkakao.presentation.session_list.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ifkakao.R
import com.example.ifkakao.databinding.FilterListItemBinding
import com.example.ifkakao.databinding.SessionListItemBinding

class SessionFilterMenuAdapter(private val filterSet: Array<out Any>) :
    RecyclerView.Adapter<SessionFilterMenuAdapter.ViewHolder>() {

    class ViewHolder(val binding: FilterListItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.filter_list_item, parent, false)

        return ViewHolder(FilterListItemBinding.bind(view))
    }

    override fun getItemCount(): Int = filterSet.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.apply {
            binding.tvFilterName.text = filterSet[position].toString()
        }
    }
}