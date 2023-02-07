package com.example.ifkakao.presentation.session_detail

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.ifkakao.R
import com.example.ifkakao.databinding.FragmentSessionDetailBinding
import com.example.ifkakao.domain.model.getFullDate
import com.example.ifkakao.domain.model.getTypeAndTracksString
import com.example.ifkakao.presentation.session_detail.adapter.UserAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


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

        val adapter = UserAdapter()
        binding.recyclerUser.adapter = adapter


        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.session.collectLatest { session ->
                    binding.tvSessionTitle.text = session.title
                    binding.tvCompany.text = session.company.toString()
                    binding.tvSessionTypeTracks.text = session.getTypeAndTracksString()
                    binding.tvSessionDate.text = session.getFullDate()

                    binding.youtubeWebView.webViewClient = WebViewClient()
                    binding.youtubeWebView.settings.run {
                        javaScriptEnabled = true
                        loadWithOverviewMode = true
                        useWideViewPort = true
                    }
                    binding.youtubeWebView.loadUrl(
                        "https://www.youtube.com/embed/" + viewModel.session.value.youtubeId
                    )

                    binding.tvSessionTags.text = session.tags
                    binding.tvSessionDesc.text = session.description

                    binding.buttonPresentationLink.isVisible = session.pptUrl.isNotEmpty()
                    binding.buttonPresentationLink.setOnClickListener {
                        val browserIntent = Intent(
                            Intent.ACTION_VIEW, Uri.parse(
                                session.pptUrl
                            )
                        )
                        requireActivity().packageManager.let {
                            if (browserIntent.resolveActivity(it) != null) {
                                startActivity(browserIntent)
                            }
                        }
                    }

                    binding.buttonShareSession.setOnClickListener {
                        val clipboardManager: ClipboardManager =
                            requireContext().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                        val clipData: ClipData =
                            ClipData.newPlainText("session_link", session.sessionWebLink)
                        clipboardManager.setPrimaryClip(clipData)
                    }

                    adapter.submitList(session.users)
                }
            }
        }

        binding.buttonToSessionList.setOnClickListener {
            findNavController().popBackStack()
        }


    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}