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
    private val onItemClick: (Int) -> (Unit),
    private val onLikeClick: (Int) -> (Unit),
) : ListAdapter<ResultSession, SessionListAdapter.ViewHolder>(diffCallback) {

    class ViewHolder(
        val binding: SessionGridItemBinding,
        val onItemClick: (Int) -> Unit,
        val onLikeClick: (Int) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root){
            init {
                itemView.setOnClickListener{
                    onItemClick(bindingAdapterPosition)
                }
                binding.sessionItemLikeButton.setOnClickListener {
                    onLikeClick(bindingAdapterPosition)
                }
            }
        }

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
        val view = LayoutInflater.from(parent.context).inflate(R.layout.session_grid_item,parent,false)
        return ViewHolder(SessionGridItemBinding.bind(view),onItemClick, onLikeClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        currentList[position]
        TODO("Not yet implemented")
    }
}