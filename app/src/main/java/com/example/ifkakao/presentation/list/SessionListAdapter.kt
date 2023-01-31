package com.example.ifkakao.presentation.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.ifkakao.R
import com.example.ifkakao.databinding.ItemSessionBinding
import com.example.ifkakao.domain.model.*

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
        holder.binding.sessionDateOrTimeText.text =
            when (currentList[position].sessionDay) {
                SessionDay.DAY_ONE -> "12.07"
                SessionDay.DAY_TWO -> "12.08"
                SessionDay.DAY_THREE -> "12.09"
                else -> "--.--"
            }
        holder.binding.sessionCompany.text =
            when (currentList[position].company) {
                Company.KAKAO -> "카카오"
                Company.KAKAO_ENTERTAINMENT -> "카카오엔터테인먼트"
                Company.KAKAO_BRAIN -> "카카오브레인"
                Company.KAKAO_BANK -> "카카오뱅크"
                Company.KAKAO_PAY -> "카카오페이"
                Company.KAKAO_MOBILITY -> "카카오모빌리티"
                Company.KAKAO_ENTERPRISE -> "카카오엔터프라이즈"
                Company.KAKAO_GAMES -> "카카오게임즈"
                Company.KAKAO_PICCOMA -> "카카오픽코마"
                Company.KRUST_UNIVERSE -> "크러스트 유니버스"
                else -> "--"
            }
        holder.binding.sessionTitle.text = currentList[position].title
        holder.binding.sessionType.text =
            when (currentList[position].sessionType) {
                SessionType.Tech -> "기술세션"
                SessionType.KeyNote -> "키노트"
                else -> ""
            }
        val tracks = currentList[position].track.map {
            when (it) {
                Track.AI -> "AI"
                Track.BE -> "백엔드"
                Track.FE -> "프론트엔드"
                Track.MOBILE -> "모바일"
                Track.CLOUD -> "클라우드"
                Track.BIG_DATA -> "빅데이터"
                Track.BLOCK_CHAIN -> "블록체인"
                Track.DEVOPS -> "DevOps"
                Track.ESG -> "ESG"
                Track.GENERAL -> "General"
                Track.CULTURE -> "Culture"
                Track.RE1015 -> "1015장애 회고"
            }
        }
        holder.binding.sessionTrack.text = tracks.joinToString(separator = "\t\t")
    }

    override fun getItemCount() = currentList.size
}