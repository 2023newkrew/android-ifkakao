package com.example.ifkakao.presentation.session_list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.ifkakao.R
import com.example.ifkakao.databinding.SessionGridItemBinding
import com.example.ifkakao.domain.model.Session

class SessionListAdapter(
    private val onItemClick: (Session) -> (Unit),
    private val onLikeClick: (Session) -> (Unit),
) : ListAdapter<Session, SessionListAdapter.ViewHolder>(diffCallback) {

    class ViewHolder(val binding: SessionGridItemBinding) : RecyclerView.ViewHolder(binding.root)

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<Session>() {
            override fun areItemsTheSame(oldItem: Session, newItem: Session): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: Session,
                newItem: Session
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
        val dayList = listOf("","12.07", "12.08", "12.09")
        holder.binding.sessionItemLikeButton.setOnClickListener {
            onLikeClick(currentList[holder.bindingAdapterPosition])
        }
        holder.itemView.setOnClickListener {
            onItemClick(currentList[holder.bindingAdapterPosition])
        }

        holder.binding.sessionItemTitle.text = currentList[position].title
        holder.binding.sessionItemDate.text = dayList[currentList[position].sessionDay]
        holder.binding.sessionItemGroup.text = currentList[position].company.toString()
        holder.binding.sessionItemKeynote.text = currentList[position].type.toString()
        if (currentList[position].isLike) {
            holder.binding.sessionItemLikeButton.background =
                ContextCompat.getDrawable(
                    holder.binding.root.context,
                    R.drawable.baseline_star_24_yellow
                )

        } else {
            holder.binding.sessionItemLikeButton.background =
                ContextCompat.getDrawable(holder.binding.root.context, R.drawable.baseline_star_24)

        }
    }
}