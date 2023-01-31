package com.example.ifkakao.presentation.home

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context.CLIPBOARD_SERVICE
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.ifkakao.*
import com.example.ifkakao.databinding.FragmentHomeBinding
import com.example.ifkakao.presentation.MainActivity
import com.example.ifkakao.presentation.home.adapter.HighlightListAdapter

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val highlightListAdapter by lazy { HighlightListAdapter(::onHighlightItemClick) }

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
        requireActivity().window.statusBarColor = requireContext().getColor(R.color.blue_primary)

        // set action bar color
        (requireActivity() as AppCompatActivity).supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        // set scroll change listener
        binding.nestedScroll.setOnScrollChangeListener { _, _, scrollY, _, _ ->
            // set status bar color
            requireActivity().window.statusBarColor = requireContext().getColor(
                if (scrollY == 0) R.color.blue_primary
                else R.color.gray_transparent
            )

            // set action bar color
            (requireActivity() as AppCompatActivity).supportActionBar?.setBackgroundDrawable(
                ColorDrawable(
                    if (scrollY == 0) Color.TRANSPARENT
                    else ContextCompat.getColor(requireContext(), R.color.gray_transparent)
                )
            )

            // set up button visibility
            binding.upButton.isVisible = scrollY > binding.nestedScroll.getChildAt(0).height / 5
        }

        // initialize banner video
        val uriPath =
            "android.resource://${requireActivity().packageName}/${R.raw.video_banner_compact}" // TODO change video dynamic
        binding.bannerVideo.setVideoURI(Uri.parse(uriPath))
        binding.bannerVideo.setOnPreparedListener {
            it.isLooping = true
            it.start()

            // set layout height
            binding.bannerVideo.layoutParams.height = WRAP_CONTENT
        }

        // initialize highlight recycler view
        val highlightRecyclerView = binding.highlightList
        highlightRecyclerView.setHasFixedSize(true)
        highlightRecyclerView.layoutManager =
            GridLayoutManager(requireContext(), 2) // TODO change span dynamic
        highlightRecyclerView.adapter = highlightListAdapter
        highlightListAdapter.submitList(
            listOf(
                TRACK_KEY_AI,
                TRACK_KEY_BACKEND,
                TRACK_KEY_CLOUD,
                TRACK_KEY_DEV_OPS,
                TRACK_KEY_BLOCK_CHAIN,
                TRACK_KEY_DATA,
                TRACK_KEY_FRONTEND,
                TRACK_KEY_MOBILE
            )
        )

        // set click listener
        binding.allSessionButton.setOnClickListener {
            (requireActivity() as MainActivity).navigateToSession(null, null)
        }
        binding.keynoteCardImage.setOnClickListener {
            (requireActivity() as MainActivity).navigateToSession(TYPE_VALUE_KEYNOTE, null)
        }
        binding.devSessionCardImage.setOnClickListener {
            (requireActivity() as MainActivity).navigateToSession(TYPE_VALUE_TECH, null)
        }
        binding.devEthicsCardImage.setOnClickListener {
            (requireActivity() as MainActivity).navigateToSession(null, TRACK_VALUE_ESG)
        }
        binding.sessionButton.setOnClickListener {
            (requireActivity() as MainActivity).navigateToSession(null, null)
        }
        binding.shareBannerImage.setOnClickListener {
            val clipboardManager: ClipboardManager = requireContext().getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
            val clipData: ClipData = ClipData.newPlainText(CLIP_LABEL_SHARE, URL_SHARE)
            clipboardManager.setPrimaryClip(clipData)
        }
        binding.upButton.setOnClickListener {
            binding.nestedScroll.smoothScrollTo(0, 0)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun onHighlightItemClick(position: Int) {
        (requireActivity() as MainActivity).navigateToSession(
            null,
            when (position) {
                HIGHLIGHT_POSITION_AI -> TRACK_VALUE_AI
                HIGHLIGHT_POSITION_BACKEND -> TRACK_VALUE_BACKEND
                HIGHLIGHT_POSITION_CLOUD -> TRACK_VALUE_CLOUD
                HIGHLIGHT_POSITION_DEV_OPS -> TRACK_VALUE_DEV_OPS
                HIGHLIGHT_POSITION_BLOCK_CHAIN -> TRACK_VALUE_BLOCK_CHAIN
                HIGHLIGHT_POSITION_DATA -> TRACK_VALUE_DATA
                HIGHLIGHT_POSITION_FRONTEND -> TRACK_VALUE_FRONTEND
                HIGHLIGHT_POSITION_MOBILE -> TRACK_VALUE_MOBILE
                HIGHLIGHT_POSITION_ESG -> TRACK_VALUE_ESG
                else -> null
            }
        )
    }
}