package com.example.ifkakao.presentation.list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.ifkakao.R
import com.example.ifkakao.databinding.ItemFilterBinding
import com.example.ifkakao.domain.model.FilterType

class FilterListAdapter(
    private val onFilterChecked: (FilterType) -> Unit,
    private val onFilterUnChecked: (FilterType) -> Unit,
) : ListAdapter<FilterType, FilterListAdapter.ViewHolder>(diffUtil) {

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<FilterType>() {
            override fun areItemsTheSame(oldItem: FilterType, newItem: FilterType): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: FilterType, newItem: FilterType): Boolean {
                return oldItem.hashCode() == newItem.hashCode()
            }
        }
    }

    class ViewHolder(
        val binding: ItemFilterBinding,
        private val onFilterChecked: (FilterType) -> Unit,
        private val onFilterUnChecked: (FilterType) -> Unit,
    ) : RecyclerView.ViewHolder(binding.root) {
        init {
//            binding.filterCheckbox.setOnCheckedChangeListener { buttonView, isChecked ->
//                if (isChecked) {
//                    onFilterChecked()
//                } else {
//
//                }
//            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_filter, parent, false)
        return ViewHolder(ItemFilterBinding.bind(view), onFilterChecked, onFilterUnChecked)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.filterCheckbox.text = currentList[position].toString()
        holder.binding.filterCheckbox.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                onFilterChecked(currentList[position])
            } else {
                onFilterUnChecked(currentList[position])
            }
        }
    }

    override fun getItemCount() = currentList.size

}