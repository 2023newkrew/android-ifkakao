package com.example.ifkakao.presentation.detail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ifkakao.R
import com.example.ifkakao.databinding.ItemUserBinding
import com.example.ifkakao.domain.model.User

class UserListAdapter : ListAdapter<User, UserListAdapter.ViewHolder>(diffUtil) {
    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<User>() {
            override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
                return oldItem.hashCode() == newItem.hashCode()
            }
        }
    }

    class ViewHolder(
        val binding: ItemUserBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        init {
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        return ViewHolder(ItemUserBinding.bind(view))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val context = holder.itemView.context
        // 분기 타줘야 할듯
        holder.binding.userNameTextView.text = currentList[position].id
        holder.binding.userIntroTextView.text = currentList[position].intro
        Glide.with(context).load(currentList[position].img).circleCrop()
            .into(holder.binding.userImageView)
    }

    override fun getItemCount() = currentList.size
}