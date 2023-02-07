package com.example.ifkakao.presentation.session_detail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.ifkakao.R
import com.example.ifkakao.databinding.DetailUserItemBinding
import com.example.ifkakao.domain.model.User

class UserAdapter : ListAdapter<User, UserAdapter.ViewHolder>(diffCallback) {
    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<User>() {
            override fun areItemsTheSame(
                oldItem: User,
                newItem: User
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: User,
                newItem: User
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

    class ViewHolder(val binding: DetailUserItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.detail_user_item, parent, false)

        return ViewHolder(DetailUserItemBinding.bind(view))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.apply {
            val user = currentList[bindingAdapterPosition]
            binding.userId.text = user.id
            binding.userIntro.text = user.intro
            binding.imageUserThumb.load(user.imageUrl)
        }
    }

}