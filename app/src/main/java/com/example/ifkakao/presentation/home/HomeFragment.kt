package com.example.ifkakao.presentation.home

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.ifkakao.R
import com.example.ifkakao.databinding.FragmentHomeBinding
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

        val adapter = HighlightTrackAdapter()
        binding.highlightTrackView.recyclerHighlightTrack.adapter = adapter
        adapter.submitList(HighlightTrack.values().toList())


        binding.nestedScrollView.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener
        { _, _, scrollY, _, _ ->
            // set action bar color
            (requireActivity() as AppCompatActivity).supportActionBar?.setBackgroundDrawable(
                ColorDrawable(
                    if (scrollY == 0) Color.TRANSPARENT
                    else ContextCompat.getColor(requireContext(), R.color.black_80)
                )
            )
        })

    }

    private fun initializePlayer() {
        player = ExoPlayer.Builder(requireContext())
            .build()
            .also { exoPlayer ->
                binding.videoView.player = exoPlayer
                val mediaItem =
                    MediaItem.fromUri(VIDEO_URL)
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