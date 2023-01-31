package com.example.ifkakao.presentation.session.detail

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.ifkakao.ARG_KEY_INFO
import com.example.ifkakao.databinding.FragmentSessionDetailBinding
import com.example.ifkakao.domain.model.Info

class DetailFragment : Fragment() {
    private var _binding: FragmentSessionDetailBinding? = null
    private val binding get() = _binding!!
    private val viewModel: DetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSessionDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // initialize info from arguments
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arguments?.getParcelable(ARG_KEY_INFO, Info::class.java)?.let {
                viewModel.info = it
            }
        } else {
            arguments?.getParcelable<Info>(ARG_KEY_INFO)?.let {
                viewModel.info = it
            }
        }
    }
}