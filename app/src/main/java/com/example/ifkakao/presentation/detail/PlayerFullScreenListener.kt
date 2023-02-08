package com.example.ifkakao.presentation.detail

import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.os.Build
import android.view.*
import android.view.View.GONE
import android.view.View.VISIBLE
import com.example.ifkakao.databinding.FragmentDetailBinding
import com.example.ifkakao.presentation.MainActivity
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerFullScreenListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

class PlayerFullScreenListener(
    private val playerUi: YouTubePlayerView,
    private val activity: MainActivity,
    private val binding: FragmentDetailBinding,
    private val viewModel: DetailViewModel,
) : YouTubePlayerFullScreenListener {

    override fun onYouTubePlayerEnterFullScreen() {
        val viewParams: ViewGroup.LayoutParams = playerUi.layoutParams
        viewParams.height = ViewGroup.LayoutParams.MATCH_PARENT
        viewParams.width = ViewGroup.LayoutParams.MATCH_PARENT
        playerUi.layoutParams = viewParams

        if (viewModel.detailFragmentState.value.orientation == Configuration.ORIENTATION_PORTRAIT) {
            activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        }

        activity.hideActionBar()
        binding.topMarginView.visibility = GONE
        hideSystemUI(activity.window)
    }


    override fun onYouTubePlayerExitFullScreen() {
        val viewParams = playerUi.layoutParams
        viewParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
        viewParams.width = ViewGroup.LayoutParams.MATCH_PARENT
        playerUi.layoutParams = viewParams

        if (viewModel.detailFragmentState.value.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
        }


        activity.showActionBar()
        binding.topMarginView.visibility = VISIBLE
        showSystemUI(activity.window)
    }
}

private fun hideSystemUI(window: Window) {
    window.setFlags(
        WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
        WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
    )
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        window.insetsController?.apply {
            setSystemBarsAppearance(
                WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS,
                WindowInsetsController.APPEARANCE_LIGHT_NAVIGATION_BARS,
            )
            systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            hide(
                WindowInsets.Type.statusBars()
                        or WindowInsets.Type.navigationBars()
                        or WindowInsets.Type.systemBars()
            )
        }
    } else {
        window.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_IMMERSIVE
                        or View.SYSTEM_UI_FLAG_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION)

    }
}

private fun showSystemUI(window: Window) {
    window.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        window.insetsController?.apply {
            setSystemBarsAppearance(
                WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS,
                WindowInsetsController.APPEARANCE_LIGHT_NAVIGATION_BARS
            )
            systemBarsBehavior = WindowInsetsController.BEHAVIOR_DEFAULT
            show(
                WindowInsets.Type.statusBars()
                        or WindowInsets.Type.navigationBars()
                        or WindowInsets.Type.systemBars()
            )
        }
    } else {
        window.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE)
    }

}
