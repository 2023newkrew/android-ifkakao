package com.example.ifkakao.presentation.main.view

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import androidx.fragment.app.Fragment
import com.example.ifkakao.R
import com.example.ifkakao.databinding.FragmentMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment(R.layout.fragment_main) {

    private var _binding: FragmentMainBinding?= null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater,container,false)

        val view = binding.root
        val videoView = binding.mainVideoView
        val uri = Uri.parse("https://t1.kakaocdn.net/inhouse_daglona/ifkakao_2022/assets/mobile_2nd_day_1080x1848.mp4")
        videoView.setVideoURI(uri)

        videoView.setOnPreparedListener{
            it.isLooping = true
            it.start()
        }


        return view
    }

}