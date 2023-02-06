package com.example.ifkakao.presentation.session_list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.ifkakao.R
import com.example.ifkakao.databinding.FilterListItemBinding


data class FilterListItem(
    val isChecked: Boolean,
    val label: String
)

class SessionFilterMenuAdapter(private val onCheck: (Int, Boolean) -> Unit) :
    ListAdapter<FilterListItem, SessionFilterMenuAdapter.ViewHolder>(diffCallback) {

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<FilterListItem>() {
            override fun areItemsTheSame(
                oldItem: FilterListItem,
                newItem: FilterListItem
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: FilterListItem,
                newItem: FilterListItem
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

    class ViewHolder(val binding: FilterListItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.filter_list_item, parent, false)

        return ViewHolder(FilterListItemBinding.bind(view))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.apply {
            binding.checkboxFilter.text = currentList[position].label
            binding.checkboxFilter.setOnCheckedChangeListener(null)
            binding.checkboxFilter.isChecked = currentList[position].isChecked

            binding.checkboxFilter.setOnCheckedChangeListener { _, isChecked ->
                onCheck(position, isChecked)
            }
        }
    }
}