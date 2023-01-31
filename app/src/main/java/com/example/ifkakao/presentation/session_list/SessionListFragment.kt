package com.example.ifkakao.presentation.session_list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.ifkakao.R
import com.example.ifkakao.databinding.FilterListBinding
import com.example.ifkakao.databinding.FragmentSessionListBinding
import com.example.ifkakao.domain.model.Company
import com.example.ifkakao.domain.model.SessionType
import com.example.ifkakao.domain.model.Track
import com.example.ifkakao.presentation.session_list.adapter.SessionFilterMenuAdapter
import com.example.ifkakao.presentation.session_list.adapter.SessionListAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

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

        val sessionListAdapter = SessionListAdapter()
        binding.sessionList.adapter = sessionListAdapter

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.sessionListState.collectLatest { sessionListState ->

                    binding.tvSessionCount.text = sessionListState.sessionList.size.toString()
                    sessionListAdapter.submitList(sessionListState.sessionList)

                }
            }
        }

        binding.tabSessionDay.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                val day = tab?.position ?: 0
                sessionListAdapter.changeDay(day)
                viewModel.onEvent(SessionListEvent.ChangeSessionDay(day))
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

        })


        binding.buttonFilterSessions.setOnClickListener {
            binding.drawerLayout.open()
        }

        filterSetting(binding.sessionFilterMenu.typeFilter, "유형", SessionType.values())
        filterSetting(binding.sessionFilterMenu.trackFilter, "트랙", Track.values())
        filterSetting(binding.sessionFilterMenu.companyFilter, "소속", Company.values())

    }

    private fun filterSetting(
        filterListBinding: FilterListBinding,
        name: String,
        filterData: Array<out Any>
    ) {
        filterListBinding.tvFilterName.text = name
        filterListBinding.tvFilterCount.text = filterData.size.toString()
        filterListBinding.recyclerFilterItems.adapter = SessionFilterMenuAdapter(filterData)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}