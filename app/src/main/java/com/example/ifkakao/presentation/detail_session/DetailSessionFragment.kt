package com.example.ifkakao.presentation.detail_session

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.example.ifkakao.databinding.FragmentDetailSessionBinding
import com.example.ifkakao.domain.model.Session
import com.example.ifkakao.presentation.main_activity.MainActivityListener

class DetailSessionFragment : Fragment() {

    private lateinit var onBackPressedCallback: OnBackPressedCallback

    private var _binding: FragmentDetailSessionBinding? = null
    private val binding get() = _binding!!

    private lateinit var parentListener: MainActivityListener

    val session by lazy {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requireArguments().getSerializable("session", Session::class.java)
        } else {
            requireArguments().getSerializable("session") as? Session
        } ?: Session()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is MainActivityListener) {
            parentListener = context
        } else {
            throw ClassCastException(
                context.toString()
                        + " must implement OnFragmentInteractionListener"
            )
        }
        onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                parentListener.goToFragment(MainActivityListener.Code.SESSION_LIST)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailSessionBinding.inflate(inflater, container, false)

        binding.titleText.text = session.title
        binding.companyText.text = session.company
        binding.typeText.text = session.type
        val tempText = " " + session.track.toString()
        binding.trackText.text = tempText
        binding.dateText.text = session.date
//        binding.sessionWebView = TODO()
        binding.tagText.text = session.tag
        binding.descriptionText.text = session.description
//        user description


        binding.sessionWebView.layoutParams.height =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                val windowMetrics = requireActivity().windowManager.currentWindowMetrics
                windowMetrics.bounds.width() * 9 / 16
            } else {
                val metrics = DisplayMetrics()
                requireActivity().windowManager.defaultDisplay.getMetrics(metrics)
                metrics.widthPixels * 9 / 16
            }
        binding.sessionWebView.webViewClient = WebViewClient()
        binding.sessionWebView.settings.run {
            javaScriptEnabled = true
            loadWithOverviewMode = true
            useWideViewPort = true
        }
        binding.sessionWebView.loadUrl("https://www.youtube.com/embed/" + session.videoLink.split("/").last())

        return binding.root
    }

    override fun onDetach() {
        super.onDetach()
        onBackPressedCallback.remove()
    }
}