package com.example.ifkakao.presentation.session

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import com.example.ifkakao.ARG_KEY_TRACK
import com.example.ifkakao.ARG_KEY_TYPE
import com.example.ifkakao.R
import com.example.ifkakao.URL_SCHEDULE
import com.example.ifkakao.databinding.FragmentSessionBinding
import com.example.ifkakao.presentation.session.adapter.SessionListAdapter
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SessionFragment : Fragment() {
    private var _binding: FragmentSessionBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SessionViewModel by viewModels()

    private var sessionListAdapter = SessionListAdapter()

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

        // collect state
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { state ->
                    binding.sizeText.text = state.filteredInfoList.size.toString()
                    sessionListAdapter.submitList(state.filteredInfoList)
                }
            }
        }

        // load info list
        viewModel.loadInfoList()

        // set status bar color
        requireActivity().window.statusBarColor =
            requireContext().getColor(R.color.gray_transparent)

        // set action bar color
        (requireActivity() as AppCompatActivity).supportActionBar?.setBackgroundDrawable(
            ColorDrawable(ContextCompat.getColor(requireContext(), R.color.gray_transparent))
        )

        // set scroll change listener
        binding.nestedScroll.setOnScrollChangeListener { _, _, scrollY, _, _ ->
            // set up button visibility
            binding.upButton.isVisible = scrollY > binding.nestedScroll.getChildAt(0).height / 5
        }

        // TODO initialize filter

        // initialize recycler view
        val sessionRecyclerView = binding.sessionList
        sessionRecyclerView.layoutManager =
            GridLayoutManager(requireContext(), 2)  // TODO change span dynamic
        sessionRecyclerView.adapter = sessionListAdapter

        // initialize tab layout
        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                val day = tab?.position ?: 0
                sessionListAdapter.changeDay(day)
                viewModel.filterInfoListByDay(day)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })


        // set click listener
        binding.scheduleButton.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(URL_SCHEDULE))
            startActivity(browserIntent)
        }
        binding.upButton.setOnClickListener {
            binding.nestedScroll.smoothScrollTo(0, 0)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}