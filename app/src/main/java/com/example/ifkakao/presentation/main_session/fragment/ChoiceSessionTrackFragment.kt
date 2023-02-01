package com.example.ifkakao.presentation.main_session.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.ifkakao.R
import com.example.ifkakao.databinding.FragmentChoiceSessionTrackBinding
import com.example.ifkakao.presentation.main_session.adapter.TrackGridAdapter

class ChoiceSessionTrackFragment : Fragment() {

    private var _binding: FragmentChoiceSessionTrackBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChoiceSessionTrackBinding.inflate(inflater, container, false)

        val dataList = mutableListOf<Pair<Int, String>>(
            Pair(R.drawable.track_01_ai,"AI"),
            Pair(R.drawable.track_02_be,"Backend"),
            Pair(R.drawable.track_03_cd,"Cloud"),
            Pair(R.drawable.track_04_do,"DevOps"),
            Pair(R.drawable.track_05_bc,"Blockchain"),
            Pair(R.drawable.track_06_dt,"Data"),
            Pair(R.drawable.track_07_fe,"Frontend"),
            Pair(R.drawable.track_08_m,"Mobile"),
        )
        val adapter = TrackGridAdapter(dataList)
        binding.trackList.adapter = adapter
        binding.trackList.layoutManager = GridLayoutManager(activity,2)
        adapter.list = dataList

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}