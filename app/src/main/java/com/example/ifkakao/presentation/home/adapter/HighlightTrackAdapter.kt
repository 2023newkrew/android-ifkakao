package com.example.ifkakao.presentation.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.ifkakao.R
import com.example.ifkakao.databinding.HighlightTrackItemBinding
import com.example.ifkakao.presentation.home.HighlightTrack


class HighlightTrackAdapter(
    private val onHighlightTrackClick: (HighlightTrack) -> Unit
) : ListAdapter<HighlightTrack, HighlightTrackAdapter.ViewHolder>(diffCallback) {
    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<HighlightTrack>() {
            override fun areItemsTheSame(
                oldItem: HighlightTrack,
                newItem: HighlightTrack
            ): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(
                oldItem: HighlightTrack,
                newItem: HighlightTrack
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

    class ViewHolder(val binding: HighlightTrackItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.highlight_track_item, parent, false)

        return ViewHolder(HighlightTrackItemBinding.bind(view))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.apply {
            binding.imageHighlightTrack.setImageResource(currentList[position].resId)
            binding.tvHighlightTrack.text = currentList[position].alias
            binding.root.setOnClickListener {
                onHighlightTrackClick(currentList[position])
            }
        }
    }
}