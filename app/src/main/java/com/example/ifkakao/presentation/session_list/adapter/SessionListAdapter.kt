package com.example.ifkakao.presentation.session_list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.ifkakao.R
import com.example.ifkakao.databinding.SessionListItemBinding
import com.example.ifkakao.domain.model.Session
import com.example.ifkakao.domain.model.getTypeAndTracksString
import java.util.*

class SessionListAdapter :
    ListAdapter<Session, SessionListAdapter.ViewHolder>(diffCallback) {

    private var day = 0

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<Session>() {
            override fun areItemsTheSame(oldItem: Session, newItem: Session): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Session, newItem: Session): Boolean {
                return oldItem == newItem
            }
        }
    }

    class ViewHolder(val binding: SessionListItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.session_list_item, parent, false)

        return ViewHolder(SessionListItemBinding.bind(view))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.apply {
            val session = currentList[position]
            val calendar = Calendar.getInstance()
            calendar.time = Date(session.timeStamp)

            val sessionDate = "%d.%02d".format(
                calendar.get(Calendar.MONTH) + 1,
                calendar.get(Calendar.DAY_OF_MONTH)
            )
            val sessionTime = "%02d:%02d".format(
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE)
            )
            val typeAndTracks = session.getTypeAndTracksString()
            binding.tvSessionDate.text = sessionDate
            binding.tvSessionTime.text = sessionTime
            binding.tvSessionCompany.text = session.company.toString()
            binding.tvSessionTitle.text = session.title
            binding.tvSessionTypeTracks.text = typeAndTracks

            binding.tvSessionDate.isVisible = day == 0
            binding.tvSessionTime.isVisible = day != 0
        }
    }

    fun changeDay(day: Int) {
        this.day = day
        notifyItemRangeChanged(0, itemCount)
    }
}