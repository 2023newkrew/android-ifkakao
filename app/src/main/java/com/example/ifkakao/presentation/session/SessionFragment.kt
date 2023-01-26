package com.example.ifkakao.presentation.session

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.ifkakao.ARG_KEY_TRACK
import com.example.ifkakao.ARG_KEY_TYPE
import com.example.ifkakao.databinding.FragmentSessionBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SessionFragment : Fragment() {
    private var _binding: FragmentSessionBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SessionViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSessionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // initialize filter from arguments
        arguments?.getString(ARG_KEY_TYPE)?.let {
            viewModel.filterInfoListByType(it)
        }
        arguments?.getString(ARG_KEY_TRACK)?.let {
            viewModel.filterInfoListByTrack(it)
        }

        // load info list
        viewModel.loadInfoList()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}