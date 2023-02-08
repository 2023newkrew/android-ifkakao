package com.example.ifkakao.presentation.list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.ifkakao.R
import com.example.ifkakao.databinding.ItemSessionBinding
import com.example.ifkakao.domain.model.SessionInfo

class SessionListAdapter(
    private val onItemClick: (Int) -> Unit,
    private val onLikeClick: (Int) -> Unit,
) : ListAdapter<SessionInfo, SessionListAdapter.ViewHolder>(diffUtil) {
    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<SessionInfo>() {
            override fun areItemsTheSame(oldItem: SessionInfo, newItem: SessionInfo): Boolean {
                println("oldItem: ${oldItem.isLiked} \n newItem: ${newItem.isLiked}")
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: SessionInfo, newItem: SessionInfo): Boolean {
                return oldItem.hashCode() == newItem.hashCode()
            }
        }
    }

    class ViewHolder(
        private val onItemClick: (Int) -> Unit,
        private val onLikeClick: (Int) -> Unit,
        val binding: ItemSessionBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        init {
            itemView.setOnClickListener {
                onItemClick(bindingAdapterPosition)
            }
            binding.likeImageView.setOnClickListener {
                onLikeClick(bindingAdapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_session, parent, false)
        //return ViewHolder(onNoteClick, onNoteDelete, ItemSessionBinding.bind(view))
        return ViewHolder(onItemClick, onLikeClick, ItemSessionBinding.bind(view))
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val context = holder.itemView.context
        // 분기 타줘야 할듯
        holder.binding.sessionDateOrTimeText.text = currentList[position].sessionDay.toString()
        holder.binding.sessionCompany.text = currentList[position].company.toString()
        holder.binding.sessionTitle.text = currentList[position].title
        holder.binding.sessionType.text = currentList[position].sessionType.toString()
        val tracks = currentList[position].track.map {
            it.toString()
        }
        holder.binding.sessionTrack.text = tracks.joinToString(separator = "\t\t")
        println("여기! ${currentList[position].isLiked}")
        if (currentList[position].isLiked) {
            holder.binding.likeImageView.background =
                AppCompatResources.getDrawable(context, R.drawable.baseline_favorite_24)
        } else {
            holder.binding.likeImageView.background =
                AppCompatResources.getDrawable(context, R.drawable.baseline_favorite_border_24)
        }
    }

    override fun getItemCount() = currentList.size
}