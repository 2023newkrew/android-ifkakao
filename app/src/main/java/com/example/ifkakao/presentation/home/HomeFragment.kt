package com.example.ifkakao.presentation.home

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
import com.example.ifkakao.presentation.home.adapter.HighlightListAdapter

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val highlightListAdapter by lazy { HighlightListAdapter() }

    //    private val viewModel: HomeViewModel by viewModels()

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
                HIGHLIGHT_KEY_AI,
                HIGHLIGHT_KEY_BACKEND,
                HIGHLIGHT_KEY_CLOUD,
                HIGHLIGHT_KEY_DEV_OPS,
                HIGHLIGHT_KEY_BLOCK_CHAIN,
                HIGHLIGHT_KEY_DATA,
                HIGHLIGHT_KEY_FRONTEND,
                HIGHLIGHT_KEY_MOBILE
            )
        )

        // initialize up button
        binding.upButton.setOnClickListener {
            binding.nestedScroll.smoothScrollTo(0, 0)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}