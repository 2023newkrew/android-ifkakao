package com.example.ifkakao.presentation.session_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.ifkakao.R
import com.example.ifkakao.databinding.FilterListBinding
import com.example.ifkakao.databinding.FragmentSessionListBinding
import com.example.ifkakao.domain.model.Company
import com.example.ifkakao.domain.model.SessionFilterableItem
import com.example.ifkakao.domain.model.SessionType
import com.example.ifkakao.domain.model.Track
import com.example.ifkakao.presentation.session_list.adapter.FilterListItem
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

        filterSetAdapter(
            binding.sessionFilterMenu.typeFilter,
            "유형",
            SessionType.values().toList()
        )

        filterSetAdapter(
            binding.sessionFilterMenu.trackFilter,
            "트랙",
            Track.values().toList()
        )
        filterSetAdapter(
            binding.sessionFilterMenu.companyFilter,
            "소속",
            Company.values().toList()
        )

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.sessionFilterState.collectLatest { sessionFilter ->
                    filterSubmitFilterItems(
                        binding.sessionFilterMenu.typeFilter,
                        SessionType.values().map {
                            FilterListItem(
                                isChecked = it in sessionFilter.sessionTypes,
                                label = it.toString()
                            )
                        }
                    )
                    filterSubmitFilterItems(
                        binding.sessionFilterMenu.trackFilter,
                        Track.values().map {
                            FilterListItem(
                                isChecked = it in sessionFilter.tracks,
                                label = it.toString()
                            )
                        }
                    )
                    filterSubmitFilterItems(
                        binding.sessionFilterMenu.companyFilter,
                        Company.values().map {
                            FilterListItem(
                                isChecked = it in sessionFilter.companies,
                                label = it.toString()
                            )
                        }
                    )
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.sessionListState.collectLatest { sessionListState ->


                    binding.tvSessionCount.text = sessionListState.sessionList.size.toString()
                    sessionListAdapter.submitList(sessionListState.sessionList.toMutableList())

                    val color: Int = if (sessionListState.isFilterEnable) {
                        ContextCompat.getColor(requireContext(), R.color.tab_blue)
                    } else {
                        ContextCompat.getColor(requireContext(), R.color.gray)
                    }
                    binding.sessionFilterMenu.tvInitFilter.setTextColor(color)
                    binding.sessionFilterMenu.tvInitFilter.compoundDrawables.forEach {
                        it?.setTint(color)
                    }

                    binding.buttonFilterSessions.setColorFilter(color)
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

        binding.sessionFilterMenu.tvInitFilter.setOnClickListener {
            viewModel.onEvent(SessionListEvent.FilterInitialize)
        }

        binding.sessionFilterMenu.btnCloseFilterMenu.setOnClickListener {
            binding.drawerLayout.close()
        }

    }

    private fun filterSetAdapter(
        filterListBinding: FilterListBinding,
        name: String,
        filterableItems: List<SessionFilterableItem>
    ) {
        filterListBinding.tvFilterName.text = name
        val adapter = SessionFilterMenuAdapter { position: Int, isChecked: Boolean ->
            if (isChecked) {
                viewModel.onEvent(
                    SessionListEvent.AddFilterItem(
                        filterableItems[position]
                    )
                )
            } else {
                viewModel.onEvent(
                    SessionListEvent.RemoveFilterItem(
                        filterableItems[position]
                    )
                )
            }
        }
        // prevent recycler item blinking
        adapter.setHasStableIds(true)
        filterListBinding.recyclerFilterItems.adapter =
            adapter
    }

    private fun filterSubmitFilterItems(
        filterListBinding: FilterListBinding,
        filterItems: List<FilterListItem>,
    ) {
        val adapter = filterListBinding.recyclerFilterItems.adapter as SessionFilterMenuAdapter
        val enableCount = filterItems.count { it.isChecked }

        adapter.submitList(filterItems.toMutableList()) {
            if (enableCount > 0) {
                val totalText = "/${filterItems.size}"
                filterListBinding.tvFilterCountFirst.text = "$enableCount"
                filterListBinding.tvFilterCountFirst.isVisible = true
                filterListBinding.tvFilterCountSecond.text = totalText
            } else {
                filterListBinding.tvFilterCountFirst.isVisible = false
                filterListBinding.tvFilterCountSecond.text = "${filterItems.size}"
            }

        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}