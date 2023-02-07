package com.example.ifkakao.presentation.home.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.ifkakao.R
import com.example.ifkakao.databinding.FragmentChoiceSessionTrackBinding
import com.example.ifkakao.presentation.home.adapter.TrackGridAdapter
import com.example.ifkakao.presentation.main_activity.MainActivityListener

class ChoiceSessionTrackFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentChoiceSessionTrackBinding? = null
    private val binding get() = _binding!!

    private lateinit var parentListener: MainActivityListener

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is MainActivityListener) {
            parentListener = context
        } else {
            throw ClassCastException(
                context.toString()
                        + " must implement OnFragmentInteractionListener"
            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChoiceSessionTrackBinding.inflate(inflater, container, false)

        val dataList = mutableListOf(
            Pair(R.drawable.track_01_ai, "AI"),
            Pair(R.drawable.track_02_be, "Backend"),
            Pair(R.drawable.track_03_cd, "Cloud"),
            Pair(R.drawable.track_04_do, "DevOps"),
            Pair(R.drawable.track_05_bc, "Blockchain"),
            Pair(R.drawable.track_06_dt, "Data"),
            Pair(R.drawable.track_07_fe, "Frontend"),
            Pair(R.drawable.track_08_m, "Mobile"),
        )
        val adapter = TrackGridAdapter(dataList)
        binding.trackList.adapter = adapter
        binding.trackList.layoutManager = GridLayoutManager(activity, 2)
        adapter.list = dataList

        binding.sessionButton.setOnClickListener(this)

        return binding.root
    }

    override fun onClick(v: View?) {
        parentListener.callBack(MainActivityListener.Code.GO_TO_SESSION_LIST)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}