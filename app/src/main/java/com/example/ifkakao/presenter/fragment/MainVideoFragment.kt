package com.example.ifkakao.presenter.fragment

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
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

        val VIDEO_PATH = "android.resource://" + "com.example.ifkakao" + "/" + R.raw.main_video
        val uri: Uri = Uri.parse(VIDEO_PATH)

        binding.mainVideoView.setVideoURI(uri)
        binding.mainVideoView.start()
        binding.mainVideoView.setOnPreparedListener {
            it.isLooping = true
            it.start()
            binding.mainVideoView.layoutParams.height = WRAP_CONTENT
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}