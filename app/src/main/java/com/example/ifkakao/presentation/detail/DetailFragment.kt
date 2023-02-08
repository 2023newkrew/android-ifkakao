package com.example.ifkakao.presentation.detail

import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ifkakao.databinding.FragmentDetailBinding
import com.example.ifkakao.presentation.MainActivity
import com.example.ifkakao.presentation.detail.adapter.UserListAdapter
import com.google.android.exoplayer2.ExoPlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.options.IFramePlayerOptions
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.utils.loadOrCueVideo
import com.pierfrancescosoffritti.androidyoutubeplayer.core.ui.DefaultPlayerUiController


class DetailFragment : Fragment() {
    private val args: DetailFragmentArgs by navArgs()
    private val binding: FragmentDetailBinding by lazy {
        FragmentDetailBinding.inflate(
            layoutInflater
        )
    }
    private val viewModel: DetailViewModel by viewModels()
    private lateinit var userListAdapter: UserListAdapter
    private var player: ExoPlayer? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userListAdapter = UserListAdapter()
        val recyclerView = binding.userRecylerView
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = userListAdapter
        userListAdapter.submitList(args.sessionInfo.users)
        binding.contentsNestedScrollView.isNestedScrollingEnabled = false

        val tracks = args.sessionInfo.track.map {
            it.toString()
        }
        binding.titleTextView.text = args.sessionInfo.title
        binding.companyTextView.text = args.sessionInfo.company.toString()
        binding.typeTextView.text =
            args.sessionInfo.sessionType.toString() + tracks.joinToString(separator = "\t\t")
        binding.contentTextView.text = args.sessionInfo.description
        if (args.sessionInfo.ppt != "") {
            binding.pptButton.visibility = VISIBLE
            val pptUrl = args.sessionInfo.ppt
            binding.pptButton.setOnClickListener {
                val browserIntent: Intent = Intent(
                    Intent.ACTION_VIEW, Uri.parse(pptUrl)
                )
                if (browserIntent.resolveActivity(requireContext().packageManager) != null) {
                    startActivity(browserIntent)
                }
            }
        }
        binding.toListButton.setOnClickListener {
            binding.root.findNavController().navigateUp()
        }

        //setVideoScreenRatio(148, 264)
        println(args.sessionInfo.sessionVodLink)
        val videoId = args.sessionInfo.sessionVodLink.split("/").last()
        lifecycle.addObserver(binding.youtubePlayerView)

        val listener: YouTubePlayerListener = object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {

                // using pre-made custom ui
                val defaultPlayerUiController =
                    DefaultPlayerUiController(binding.youtubePlayerView, youTubePlayer)
                binding.youtubePlayerView.setCustomPlayerUi(defaultPlayerUiController.rootView)
                binding.youtubePlayerView.addFullScreenListener(
                    PlayerFullScreenListener(
                        binding.youtubePlayerView,
                        requireActivity() as MainActivity,
                        binding,
                        viewModel,
                    )
                )
                youTubePlayer.loadOrCueVideo(lifecycle, videoId, 0f)
            }
        }

        // disable web ui
        val options: IFramePlayerOptions = IFramePlayerOptions.Builder().controls(0).build()

        binding.youtubePlayerView.initialize(listener, options)

        binding.youtubePlayerView.isFullScreen()

//        binding.youtubePlayerView.addYouTubePlayerListener(
//            object : AbstractYouTubePlayerListener() {
//                override fun onReady(youTubePlayer: YouTubePlayer) {
//                    youTubePlayer.cueVideo(videoId, 0f)
//                }
//            }
//        )
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        viewModel.setOrientation(newConfig.orientation)
        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            binding.youtubePlayerView.enterFullScreen()
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            binding.youtubePlayerView.exitFullScreen()
        }
    }

//    fun setVideoScreenRatio(height: Int, width: Int) {
//        val wm = requireContext().getSystemService(Context.WINDOW_SERVICE) as WindowManager
//        var dpWidth = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
//            val windowMetrics = wm.currentWindowMetrics
//            val insets = windowMetrics.windowInsets
//                .getInsetsIgnoringVisibility(WindowInsets.Type.systemBars())
//            windowMetrics.bounds.width() - insets.left - insets.right
//        } else {
//            val displayMetrics = DisplayMetrics()
//            wm.defaultDisplay.getMetrics(displayMetrics)
//            displayMetrics.widthPixels
//        }
//        val screenWidth = dpWidth
//        binding.videoPlayer.layoutParams.height =
//            (height.toFloat() / width.toFloat() *
//                    screenWidth.toFloat()).toInt()
//        binding.videoPlayer.layoutParams = binding.videoPlayer.layoutParams
//    }

//    override fun onStart() {
//        super.onStart()
//        val youtubeLink = "https://www.youtube.com/watch?v=" + args.sessionInfo.sessionVodLink.split("/").last()
//        if (viewModel.videoPlayerState.value.videoUrl.isNotBlank()){
//            if (Util.SDK_INT >= 24) {
//                initializePlayer()
//            }
//        } else {
//            object : YouTubeExtractor(requireContext()) {
//                override fun onExtractionComplete(ytFiles: SparseArray<YtFile>?, vMeta: VideoMeta?) {
//                    if (ytFiles != null) {
//                        val itag = 136
//                        // 133 -> 240p
//                        // 18 -> 360p
//                        // 22 -> 720p
//                        val videoPath: String = ytFiles[itag].url
//                        println("************************")
//                        println("${ytFiles}")
//                        viewModel.setVideoUrl(videoPath)
//                        if (Util.SDK_INT >= 24) {
//                            initializePlayer()
//                        }
//                    }
//
//                }
//            }.extract(youtubeLink)
//        }
//
//    }
//
//    override fun onResume() {
//        super.onResume()
//        //hideSystemUi()
//        val youtubeLink = "https://www.youtube.com/watch?v=" + args.sessionInfo.sessionVodLink.split("/").last()
//        if ((Util.SDK_INT < 24 || player == null)) {
//            initializePlayer()
//        }
//
//    }
//
//    override fun onPause() {
//        super.onPause()
//        if (Util.SDK_INT < 24) {
//            releasePlayer()
//        }
//    }
//
//    override fun onStop() {
//        super.onStop()
//        if (Util.SDK_INT >= 24) {
//            releasePlayer()
//        }
//    }
//
//    private fun initializePlayer() {
//        val trackSelector = DefaultTrackSelector(requireContext()).apply {
//            setParameters(buildUponParameters().setMaxVideoSizeSd())
//        }
//        player = ExoPlayer.Builder(requireContext())
//            .setTrackSelector(trackSelector)
//            .build()
//            .also { exoPlayer ->
//                binding.videoPlayer.player = exoPlayer
//                // 가변 품질 스트리밍 적용
//                val mediaItem = MediaItem.Builder()
//                    .setUri(viewModel.videoPlayerState.value.videoUrl)
//                    .setMimeType(MimeTypes.APPLICATION_MP4)
//                    .build()
//                exoPlayer.setMediaItem(mediaItem)
//                exoPlayer.repeatMode = Player.REPEAT_MODE_OFF
//                //exoPlayer.playWhenReady = viewModel.videoPlayerState.value.playWhenReady
//                exoPlayer.seekTo(
//                    viewModel.videoPlayerState.value.currentWindow,
//                    viewModel.videoPlayerState.value.playbackPosition
//                )
//                exoPlayer.prepare()
//            }
//    }
//
//    private fun releasePlayer() {
//        player?.run {
//            viewModel.updateVideoPlayerState(
//                currentWindow = this.currentMediaItemIndex,
//                playbackPosition = this.currentPosition,
//                playWhenReady = this.playWhenReady
//            )
//            release()
//        }
//        player = null
//    }
//


}