package com.example.ifkakao.presentation.main

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.ifkakao.R
import com.example.ifkakao.databinding.FragmentMainBinding
import com.example.ifkakao.domain.model.SessionType
import com.example.ifkakao.domain.model.Track
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.util.Util
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment() {
    private val binding: FragmentMainBinding by lazy { FragmentMainBinding.inflate(layoutInflater) }
    private var player: ExoPlayer? = null
    private var playWhenReady = true
    private var currentWindow = 0
    private var playbackPosition = 0L

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.nestedScrollView.setOnScrollChangeListener { _, _, scrollY, _, _ ->
            // set status bar color
            requireActivity().window.statusBarColor = requireContext().getColor(
                if (scrollY == 0) R.color.deepskyblue
                else R.color.black_80
            )
            // set action bar color
            (requireActivity() as AppCompatActivity).supportActionBar?.setBackgroundDrawable(
                ColorDrawable(
                    if (scrollY == 0) Color.TRANSPARENT
                    else ContextCompat.getColor(requireContext(), R.color.black_80)
                )
            )
        }

        binding.floatingUpButton.setOnClickListener {
            binding.nestedScrollView.smoothScrollTo(0, 0)
        }

        binding.toAllSessionButton.setOnClickListener {
            val action = MainFragmentDirections.actionMainToList()
            view.findNavController().navigate(action)
        }

        binding.toKeynoteButton.setOnClickListener {
            val action = MainFragmentDirections.actionMainToList(sessionType = SessionType.KeyNote)
            view.findNavController().navigate(action)
        }

        binding.toTechSessionButton.setOnClickListener {
            val action = MainFragmentDirections.actionMainToList(sessionType = SessionType.Tech)
            view.findNavController().navigate(action)
        }

        binding.toTechEthicButton.setOnClickListener {
            val action = MainFragmentDirections.actionMainToList(sessionTrack = Track.ESG)
            view.findNavController().navigate(action)
        }

        // highlight track
        binding.aiButton.setOnClickListener {
            val action = MainFragmentDirections.actionMainToList(sessionTrack = Track.AI)
            view.findNavController().navigate(action)
        }
        binding.beButton.setOnClickListener {
            val action = MainFragmentDirections.actionMainToList(sessionTrack = Track.BE)
            view.findNavController().navigate(action)
        }
        binding.cloudButton.setOnClickListener {
            val action = MainFragmentDirections.actionMainToList(sessionTrack = Track.CLOUD)
            view.findNavController().navigate(action)
        }
        binding.devopsButton.setOnClickListener {
            val action = MainFragmentDirections.actionMainToList(sessionTrack = Track.DEVOPS)
            view.findNavController().navigate(action)
        }
        binding.blockchainButton.setOnClickListener {
            val action = MainFragmentDirections.actionMainToList(sessionTrack = Track.BLOCK_CHAIN)
            view.findNavController().navigate(action)
        }
        binding.dataButton.setOnClickListener {
            val action = MainFragmentDirections.actionMainToList(sessionTrack = Track.BIG_DATA)
            view.findNavController().navigate(action)
        }
        binding.feButton.setOnClickListener {
            val action = MainFragmentDirections.actionMainToList(sessionTrack = Track.FE)
            view.findNavController().navigate(action)
        }
        binding.mobileButton.setOnClickListener {
            val action = MainFragmentDirections.actionMainToList(sessionTrack = Track.MOBILE)
            view.findNavController().navigate(action)
        }

        // to session
        binding.toSessionButton.setOnClickListener {
            val action = MainFragmentDirections.actionMainToList()
            view.findNavController().navigate(action)
        }
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

    private fun initializePlayer() {
        player = ExoPlayer.Builder(requireContext())
            .build()
            .also { exoPlayer ->
                binding.mainVideo.player = exoPlayer
                val mediaItem = MediaItem.fromUri(getString(R.string.main_video_url_mp4))
                exoPlayer.setMediaItem(mediaItem)
                exoPlayer.repeatMode = Player.REPEAT_MODE_ALL
                exoPlayer.playWhenReady = playWhenReady
                exoPlayer.seekTo(currentWindow, playbackPosition)
                exoPlayer.prepare()
            }
    }

    private fun releasePlayer() {
        player?.run {
            playbackPosition = this.currentPosition
            currentWindow = this.currentMediaItemIndex
            playWhenReady = this.playWhenReady
            release()
        }
        player = null
    }
}