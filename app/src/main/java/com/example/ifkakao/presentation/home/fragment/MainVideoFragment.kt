package com.example.ifkakao.presentation.home.fragment

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.ifkakao.R
import com.example.ifkakao.databinding.FragmentMainVideoBinding

class MainVideoFragment : Fragment() {
    private var _binding: FragmentMainVideoBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentMainVideoBinding.inflate(inflater, container, false)

        val videoPath = "android.resource://" + (activity?.packageName ?: "com.example.ifkakao") + "/" + R.raw.main_video
        val uri: Uri = Uri.parse(videoPath)

        binding.mainVideoView.setVideoURI(uri)
        binding.mainVideoView.setOnPreparedListener {
            it.isLooping = true
            it.start()
            binding.mainVideoView.layoutParams.height = it.videoHeight
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}