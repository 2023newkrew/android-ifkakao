package com.example.ifkakao.presentation.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.ifkakao.*
import com.example.ifkakao.databinding.ItemHighlightBinding

class HighlightListAdapter(
    val onItemClick: (Int) -> Unit
) : ListAdapter<String, HighlightListAdapter.ViewHolder>(diffUtil) {
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

    class ViewHolder(
        val onItemClick: (Int) -> Unit,
        val binding: ItemHighlightBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        init {
            itemView.setOnClickListener {
                onItemClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_highlight, parent, false)
        return ViewHolder(onItemClick, ItemHighlightBinding.bind(view))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.highlightImage.setImageResource(
            when (currentList[position]) {
                TRACK_KEY_AI -> R.drawable.image_highlight_ai
                TRACK_KEY_BACKEND -> R.drawable.image_highlight_backend
                TRACK_KEY_CLOUD -> R.drawable.image_highlight_cloud
                TRACK_KEY_DEV_OPS -> R.drawable.image_highlight_dev_ops
                TRACK_KEY_BLOCK_CHAIN -> R.drawable.image_highlight_blockchain
                TRACK_KEY_DATA -> R.drawable.image_highlight_data
                TRACK_KEY_FRONTEND -> R.drawable.image_highlight_frontend
                TRACK_KEY_MOBILE -> R.drawable.image_highlight_mobile
                else -> 0
            }
        )
        holder.binding.highlightTitle.setText(
            when (currentList[position]) {
                TRACK_KEY_AI -> R.string.highlight_ai
                TRACK_KEY_BACKEND -> R.string.highlight_backend
                TRACK_KEY_CLOUD -> R.string.highlight_cloud
                TRACK_KEY_DEV_OPS -> R.string.highlight_dev_ops
                TRACK_KEY_BLOCK_CHAIN -> R.string.highlight_blockchain
                TRACK_KEY_DATA -> R.string.highlight_data
                TRACK_KEY_FRONTEND -> R.string.highlight_frontend
                TRACK_KEY_MOBILE -> R.string.highlight_mobile
                else -> 0
            }
        )
    }

    override fun getItemCount(): Int = currentList.size
}