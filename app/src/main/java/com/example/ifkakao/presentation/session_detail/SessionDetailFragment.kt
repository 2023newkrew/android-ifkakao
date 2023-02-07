package com.example.ifkakao.presentation.session_detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.ifkakao.R
import com.example.ifkakao.databinding.FragmentSessionDetailBinding
import com.example.ifkakao.presentation.getWidthDp
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SessionDetailFragment : Fragment(R.layout.fragment_session_detail) {
    private var _binding: FragmentSessionDetailBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SessionDetailViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSessionDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // initialize youtube web view
        binding.youtubeWebView.post {
            binding.youtubeWebView.layoutParams.height = requireContext().getWidthDp() * 9 / 16
            binding.youtubeWebView.webViewClient = WebViewClient()
            binding.youtubeWebView.settings.run {
                javaScriptEnabled = true
                loadWithOverviewMode = true
                useWideViewPort = true
            }
            binding.youtubeWebView.loadUrl(
                "https://www.youtube.com/embed/" + viewModel.session.value.youtubeId
            )
        }


    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}