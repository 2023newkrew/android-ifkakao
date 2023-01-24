package com.example.ifkakao.presentation.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.ifkakao.*
import com.example.ifkakao.databinding.ItemHighlightBinding

class HighlightListAdapter : ListAdapter<String, HighlightListAdapter.ViewHolder>(diffUtil) {
    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<String>() {
            override fun areItemsTheSame(
                oldItem: String,
                newItem: String
            ): Boolean = oldItem == newItem

            override fun areContentsTheSame(
                oldItem: String,
                newItem: String
            ): Boolean = oldItem == newItem
        }
    }

    class ViewHolder(val binding: ItemHighlightBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            itemView.setOnClickListener {
                // TODO
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_highlight, parent, false)
        return ViewHolder(ItemHighlightBinding.bind(view))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.highlightImage.setImageResource(
            when (currentList[position]) {
                HIGHLIGHT_KEY_AI -> R.drawable.image_highlight_ai
                HIGHLIGHT_KEY_BACKEND -> R.drawable.image_highlight_backend
                HIGHLIGHT_KEY_CLOUD -> R.drawable.image_highlight_cloud
                HIGHLIGHT_KEY_DEV_OPS -> R.drawable.image_highlight_dev_ops
                HIGHLIGHT_KEY_BLOCK_CHAIN -> R.drawable.image_highlight_blockchain
                HIGHLIGHT_KEY_DATA -> R.drawable.image_highlight_data
                HIGHLIGHT_KEY_FRONTEND -> R.drawable.image_highlight_frontend
                HIGHLIGHT_KEY_MOBILE -> R.drawable.image_highlight_mobile
                else -> 0
            }
        )
        holder.binding.highlightTitle.setText(
            when (currentList[position]) {
                HIGHLIGHT_KEY_AI -> R.string.highlight_ai
                HIGHLIGHT_KEY_BACKEND -> R.string.highlight_backend
                HIGHLIGHT_KEY_CLOUD -> R.string.highlight_cloud
                HIGHLIGHT_KEY_DEV_OPS -> R.string.highlight_dev_ops
                HIGHLIGHT_KEY_BLOCK_CHAIN -> R.string.highlight_blockchain
                HIGHLIGHT_KEY_DATA -> R.string.highlight_data
                HIGHLIGHT_KEY_FRONTEND -> R.string.highlight_frontend
                HIGHLIGHT_KEY_MOBILE -> R.string.highlight_mobile
                else -> 0
            }
        )
    }

    override fun getItemCount(): Int = currentList.size
}