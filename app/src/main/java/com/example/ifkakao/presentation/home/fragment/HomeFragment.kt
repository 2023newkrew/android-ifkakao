package com.example.ifkakao.presentation.home.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.add
import androidx.fragment.app.commit
import com.example.ifkakao.R
import com.example.ifkakao.databinding.FragmentMainBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        if (savedInstanceState == null) {
            childFragmentManager.commit {
                setReorderingAllowed(true)
                add<MainVideoFragment>(R.id.main_video_fragment_container_view)
                add<HelloFragment>(R.id.hello_fragment_container_view)
                add<ChoiceSessionTypeFragment>(R.id.choice_session_type_fragment_container_view)
                add<ChoiceSessionTrackFragment>(R.id.choice_session_track_fragment_container_view)
                add<EndingBannerFragment>(R.id.ending_banner_fragment_container_view)
            }
        }

        return binding.root
    }
}