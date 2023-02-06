package com.example.ifkakao.presentation.home

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.ifkakao.R
import com.example.ifkakao.databinding.FragmentHomeBinding
import com.example.ifkakao.domain.model.SessionType
import com.example.ifkakao.domain.model.Track
import com.example.ifkakao.presentation.getWidthDp
import com.example.ifkakao.presentation.home.adapter.HighlightTrackAdapter
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.util.Util
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by viewModels()

    private var player: ExoPlayer? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        if (Util.SDK_INT >= 24) {
            initializePlayer()
        }
    }

    override fun onResume() {
        super.onResume()
        if ((Util.SDK_INT < 24 || player == null)) {
            initializePlayer()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = HighlightTrackAdapter {
            val action = HomeFragmentDirections.actionHomeFragmentToSessionListFragment(
                sessionType = "",
                track = it.aliasTrackEnum
            )
            findNavController().navigate(action)
        }
        binding.highlightTrackView.recyclerHighlightTrack.adapter = adapter
        adapter.submitList(HighlightTrack.values().toList())

        binding.appbar.addOnOffsetChangedListener { _, verticalOffset ->
            // set action bar color
            (requireActivity() as AppCompatActivity).supportActionBar?.setBackgroundDrawable(
                ColorDrawable(
                    if (verticalOffset == 0) Color.TRANSPARENT
                    else ContextCompat.getColor(requireContext(), R.color.black_80)
                )
            )
        }

        binding.floatingUpButton.setOnClickListener {
            binding.nestedScrollView.smoothScrollTo(0, 0)
            binding.appbar.setExpanded(true, true)
        }

        binding.seeYouLaterView.toAllSessionButton.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToSessionListFragment()
            findNavController().navigate(action)
        }

        binding.cardListView.keynoteCardImage.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToSessionListFragment(
                sessionType = SessionType.KeyNote.alias,
                track = ""
            )
            findNavController().navigate(action)
        }

        binding.cardListView.techCardImage.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToSessionListFragment(
                sessionType = SessionType.TechSession.alias,
                track = ""
            )
            findNavController().navigate(action)
        }

        binding.cardListView.socialCardImage.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToSessionListFragment(
                sessionType = "",
                track = Track.Esg.alias
            )
            findNavController().navigate(action)
        }

        binding.highlightTrackView.toAllSessionButton.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToSessionListFragment()
            findNavController().navigate(action)
        }
    }

    private fun initializePlayer() {
        player = ExoPlayer.Builder(requireContext()).build().also { exoPlayer ->
            binding.videoView.player = exoPlayer

            val videoUri: String = if (requireContext().getWidthDp() >= 600) {
                VIDEO_URL_LAND
            } else {
                VIDEO_URL_PORT
            }
            val mediaItem = MediaItem.fromUri(videoUri)
            exoPlayer.setMediaItem(mediaItem)
            exoPlayer.repeatMode = Player.REPEAT_MODE_ALL
            exoPlayer.playWhenReady = true
            exoPlayer.seekTo(
                viewModel.homeState.value.currentWindow,
                viewModel.homeState.value.playbackPosition
            )
            exoPlayer.prepare()
        }
    }

    private fun releasePlayer() {
        player?.run {
            viewModel.onEvent(
                HomeEvent.ReleasePlayer(
                    currentWindow = currentMediaItemIndex,
                    playbackPosition = currentPosition,
                    playWhenReady = playWhenReady
                )
            )
            release()
        }
        player = null
    }

    override fun onPause() {
        super.onPause()
        if (Util.SDK_INT < 24) {
            releasePlayer()
        }
    }

    override fun onStop() {
        super.onStop()
        if (Util.SDK_INT >= 24) {
            releasePlayer()
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}