package com.example.ifkakao.presentation.session.detail

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.ifkakao.ARG_KEY_INFO
import com.example.ifkakao.CLIP_LABEL_SHARE
import com.example.ifkakao.databinding.FragmentSessionDetailBinding
import com.example.ifkakao.domain.model.Info
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment() {
    private var _binding: FragmentSessionDetailBinding? = null
    private val binding get() = _binding!!
    private val viewModel: DetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSessionDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // initialize info from arguments
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arguments?.getParcelable(ARG_KEY_INFO, Info::class.java)?.let {
                viewModel.info = it
            }
        } else {
            arguments?.getParcelable<Info>(ARG_KEY_INFO)?.let {
                viewModel.info = it
            }
        }

        // set text
        viewModel.info?.let {
            val trackString = it.track.joinToString(separator = "   ")
            val trackText = it.sessionType + "   " + trackString
            val dateText = "2022." + it.sessionDate

            binding.detailTitleText.text = it.title
            binding.detailCompanyText.text = it.company
            binding.detailTrackText.text = trackText
            binding.detailDateText.text = dateText
            binding.detailTagsText.text = it.tags
            binding.detailDescriptionText.text = it.description

            binding.detailPresentationButton.visibility =
                if (it.ppt.isNotEmpty()) VISIBLE
                else GONE

            // user 1
            if (it.user1Id.isNotEmpty()) {
                binding.detailUser1.userLayout.visibility = VISIBLE
                Glide.with(requireContext())
                    .load(it.user1Image)
                    .into(binding.detailUser1.userImage)
                binding.detailUser1.userName.text = it.user1Id
                binding.detailUser1.userDescription.text = it.user1Intro
            } else {
                binding.detailUser1.userLayout.visibility = GONE
            }

            // user 2
            if (it.user2Id.isNotEmpty()) {
                binding.detailUser2.userLayout.visibility = VISIBLE
                Glide.with(requireContext())
                    .load(it.user2Image)
                    .into(binding.detailUser2.userImage)
                binding.detailUser2.userName.text = it.user2Id
                binding.detailUser2.userDescription.text = it.user2Intro
            } else {
                binding.detailUser2.userLayout.visibility = GONE
            }

            // user 3
            if (it.user3Id.isNotEmpty()) {
                binding.detailUser3.userLayout.visibility = VISIBLE
                Glide.with(requireContext())
                    .load(it.user3Image)
                    .into(binding.detailUser3.userImage)
                binding.detailUser3.userName.text = it.user3Id
                binding.detailUser3.userDescription.text = it.user3Intro
            } else {
                binding.detailUser3.userLayout.visibility = GONE
            }
        }

        // initialize youtube web view
        binding.youtubeWebView.run {
            layoutParams.height = viewModel.getWidth() * 9 / 16
            webViewClient = WebViewClient()
            settings.run {
                javaScriptEnabled = true
                loadWithOverviewMode = true
                useWideViewPort = true
            }
            loadUrl("https://www.youtube.com/embed/" + viewModel.info?.sessionVodLink?.split("/")?.last())
        }

        // set on click listener
        binding.detailBackLayout.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
        binding.detailShareButton.setOnClickListener {
            val clipboardManager: ClipboardManager = requireContext().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clipData: ClipData = ClipData.newPlainText(CLIP_LABEL_SHARE, viewModel.info?.sessionVodLink)
            clipboardManager.setPrimaryClip(clipData)
        }
        binding.detailPresentationButton.setOnClickListener {
            Intent(Intent.ACTION_VIEW, Uri.parse(viewModel.info?.ppt))
                .also {
                    it.resolveActivity(requireContext().packageManager)?.run {
                        startActivity(it)
                    }
                }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        // prevent back to home
        val callback: OnBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                parentFragmentManager.popBackStack()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }
}