package com.example.ifkakao.presentation.session.select.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.ifkakao.*
import com.example.ifkakao.databinding.ItemFilterBinding

class FilterListAdapter(
    private val filterCode: Int,
    private val onItemClick: (Int) -> Unit
) : ListAdapter<Pair<String, Boolean>, FilterListAdapter.ViewHolder>(diffUtil) {
    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<Pair<String, Boolean>>() {
            override fun areItemsTheSame(
                oldItem: Pair<String, Boolean>,
                newItem: Pair<String, Boolean>
            ): Boolean = oldItem.first == newItem.first

            override fun areContentsTheSame(
                oldItem: Pair<String, Boolean>,
                newItem: Pair<String, Boolean>
            ): Boolean = oldItem == newItem
        }
    }

    class ViewHolder(
        val binding: ItemFilterBinding,
        val onItemClick: (Int) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.checkBox.setOnClickListener {
                onItemClick(adapterPosition)
                binding.checkBox.isChecked = !binding.checkBox.isChecked
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_filter, parent, false)
        return ViewHolder(ItemFilterBinding.bind(view), onItemClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.checkBox.text = currentList[position].first
        holder.binding.checkBox.isChecked = currentList[position].second
    }

    override fun getItemCount(): Int = currentList.size

    fun refresh(valueSet: Set<String>) {
        val list = when (filterCode) {
            FILTER_CODE_TYPE ->
                listOf(
                    TYPE_VALUE_KEYNOTE to valueSet.contains(TYPE_VALUE_KEYNOTE),
                    TYPE_VALUE_PREVIEW to valueSet.contains(TYPE_VALUE_PREVIEW),
                    TYPE_VALUE_TECH to valueSet.contains(TYPE_VALUE_TECH)
                )
            FILTER_CODE_TRACK ->
                listOf(
                    TRACK_VALUE_1015 to valueSet.contains(TRACK_VALUE_1015),
                    TRACK_VALUE_AI to valueSet.contains(TRACK_VALUE_AI),
                    TRACK_VALUE_BACKEND to valueSet.contains(TRACK_VALUE_BACKEND),
                    TRACK_VALUE_FRONTEND to valueSet.contains(TRACK_VALUE_FRONTEND),
                    TRACK_VALUE_MOBILE to valueSet.contains(TRACK_VALUE_MOBILE),
                    TRACK_VALUE_CLOUD to valueSet.contains(TRACK_VALUE_CLOUD),
                    TRACK_VALUE_DATA to valueSet.contains(TRACK_VALUE_DATA),
                    TRACK_VALUE_BLOCK_CHAIN to valueSet.contains(TRACK_VALUE_BLOCK_CHAIN),
                    TRACK_VALUE_DEV_OPS to valueSet.contains(TRACK_VALUE_DEV_OPS),
                    TRACK_VALUE_ESG to valueSet.contains(TRACK_VALUE_ESG),
                    TRACK_VALUE_GENERAL to valueSet.contains(TRACK_VALUE_GENERAL),
                    TRACK_VALUE_CULTURE to valueSet.contains(TRACK_VALUE_CULTURE)
                )
            FILTER_CODE_COMPANY ->
                listOf(
                    COMPANY_VALUE_KAKAO to valueSet.contains(COMPANY_VALUE_KAKAO),
                    COMPANY_VALUE_KAKAO_PA to valueSet.contains(COMPANY_VALUE_KAKAO_PA),
                    COMPANY_VALUE_KAKAO_EP to valueSet.contains(COMPANY_VALUE_KAKAO_EP),
                    COMPANY_VALUE_KAKAO_M to valueSet.contains(COMPANY_VALUE_KAKAO_M),
                    COMPANY_VALUE_KAKAO_B to valueSet.contains(COMPANY_VALUE_KAKAO_B),
                    COMPANY_VALUE_KAKAO_R to valueSet.contains(COMPANY_VALUE_KAKAO_R),
                    COMPANY_VALUE_KAKAO_G to valueSet.contains(COMPANY_VALUE_KAKAO_G),
                    COMPANY_VALUE_KAKAO_ET to valueSet.contains(COMPANY_VALUE_KAKAO_ET),
                    COMPANY_VALUE_KU to valueSet.contains(COMPANY_VALUE_KU),
                    COMPANY_VALUE_KAKAO_PI to valueSet.contains(COMPANY_VALUE_KAKAO_PI)
                )
            else -> listOf()
        }
        submitList(list)
    }
}