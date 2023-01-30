package com.example.ifkakao.presentation.session_list

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.ifkakao.R
import com.example.ifkakao.databinding.FragmentSessionListBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SessionListFragment : Fragment(R.layout.fragment_session_list) {

    private var _binding: FragmentSessionListBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SessionListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSessionListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        TODO()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}