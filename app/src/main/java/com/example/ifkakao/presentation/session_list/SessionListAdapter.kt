package com.example.ifkakao.presentation.session_list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.ifkakao.R
import com.example.ifkakao.data.data_source.remote.dto.ResultSession
import com.example.ifkakao.databinding.FragmentSessionListBinding
import com.example.ifkakao.databinding.SessionGridItemBinding

class SessionListAdapter(
    private val onItemClick: (ResultSession) -> (Unit),
    private val onLikeClick: (ResultSession) -> (Unit),
) : ListAdapter<ResultSession, SessionListAdapter.ViewHolder>(diffCallback) {

    class ViewHolder(val binding: SessionGridItemBinding) : RecyclerView.ViewHolder(binding.root)

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<ResultSession>() {
            override fun areItemsTheSame(oldItem: ResultSession, newItem: ResultSession): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: ResultSession,
                newItem: ResultSession
            ): Boolean {
                return oldItem.hashCode() == newItem.hashCode()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.session_grid_item, parent, false)
        return ViewHolder(SessionGridItemBinding.bind(view))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.sessionItemLikeButton.setOnClickListener {
            onLikeClick(currentList[holder.bindingAdapterPosition])
        }
        holder.itemView.setOnClickListener {
            onItemClick(currentList[holder.bindingAdapterPosition])
        }
    }
}