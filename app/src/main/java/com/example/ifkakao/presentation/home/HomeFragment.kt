package com.example.ifkakao.presentation.home

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.ifkakao.R
import com.example.ifkakao.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // set status bar color
        requireActivity().window.statusBarColor = requireContext().getColor(R.color.blue_compressed)

        // initialize banner video
        val uriPath =
            "android.resource://${requireActivity().packageName}/${R.raw.video_banner_compact}"
        binding.videoBanner.setVideoURI(Uri.parse(uriPath))
        binding.videoBanner.setOnPreparedListener {
            it.isLooping = true
            it.start()

            // set layout height
            binding.videoBanner.layoutParams.height = WRAP_CONTENT
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}