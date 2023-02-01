package com.example.ifkakao.presentation.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.ifkakao.R
import com.example.ifkakao.databinding.ItemSessionBinding
import com.example.ifkakao.domain.model.SessionInfo

class SessionListAdapter : ListAdapter<SessionInfo, SessionListAdapter.ViewHolder>(diffUtil) {
    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<SessionInfo>() {
            override fun areItemsTheSame(oldItem: SessionInfo, newItem: SessionInfo): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: SessionInfo, newItem: SessionInfo): Boolean {
                return oldItem.hashCode() == newItem.hashCode()
            }
        }
    }

    class ViewHolder(
        //val onNoteClick: (Int) -> Unit,
        // val onNoteDelete: (Int) -> Unit,
        val binding: ItemSessionBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        init {
//            itemView.setOnClickListener {
//                onNoteClick(adapterPosition)
//            }
//            binding.deleteImageView.setOnClickListener {
//                onNoteDelete(adapterPosition)
//            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_session, parent, false)
        //return ViewHolder(onNoteClick, onNoteDelete, ItemSessionBinding.bind(view))
        return ViewHolder(ItemSessionBinding.bind(view))
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
    }

    override fun getItemCount() = currentList.size
}