package com.example.ifkakao.presentation.list

import android.content.Intent
import android.content.res.ColorStateList
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ifkakao.R
import com.example.ifkakao.databinding.FilterListBinding
import com.example.ifkakao.databinding.FragmentListBinding
import com.example.ifkakao.domain.model.*
import com.example.ifkakao.presentation.list.adapter.FilterListAdapter
import com.example.ifkakao.presentation.list.adapter.SessionListAdapter
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ListFragment : Fragment() {
    private val args: ListFragmentArgs by navArgs()
    private val binding: FragmentListBinding by lazy { FragmentListBinding.inflate(layoutInflater) }
    private val viewModel: ListViewModel by viewModels()
    private lateinit var sessionListAdapter: SessionListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding.toolbar.setTitle(R.string.label_list)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // recycler view, Adapter setting
        sessionListAdapter = SessionListAdapter()
        sessionListAdapter.setHasStableIds(true)
        val recyclerView = binding.sessionRecyclerView
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        recyclerView.adapter = sessionListAdapter

        // drawer setting
        binding.filterDrawerNestedScrollView.isNestedScrollingEnabled = false
        filterInitialize(
            binding.sessionFilterDrawerMenu.typeFilter,
            "유형",
            SessionType.values().toList()
        )
        filterInitialize(binding.sessionFilterDrawerMenu.trackFilter, "트랙", Track.values().toList())
        filterInitialize(
            binding.sessionFilterDrawerMenu.companyFilter,
            "소속",
            Company.values().toList()
        )

        // tab layout setting
        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                val day = tab?.position ?: 0
                if (day == 0) {
                    viewModel.setSessionDay(SessionDay.Null)
                } else {
                    viewModel.setSessionDay(SessionDay.from(day.toString()))
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.sessionState.collect { state ->
                    viewModel.getSessions()
                    println(state)
                    setFilterCount(binding.sessionFilterDrawerMenu.typeFilter, state.types.size)
                    setFilterCount(binding.sessionFilterDrawerMenu.trackFilter, state.tracks.size)
                    setFilterCount(
                        binding.sessionFilterDrawerMenu.companyFilter,
                        state.companies.size
                    )

                    binding.filterButton.imageTintList = ColorStateList.valueOf(
                        ContextCompat.getColor(
                            requireContext(),
                            if (state.types.isNotEmpty() || state.tracks.isNotEmpty() || state.companies.isNotEmpty()) {
                                R.color.deepskyblue
                            } else {
                                R.color.white
                            }
                        )
                    )
                }
            }

        }
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.sessionData.collect { state ->
                    val recyclerViewState = recyclerView.layoutManager?.onSaveInstanceState()
                    sessionListAdapter.submitList(state.sessionList.toMutableList()) {
                        recyclerView.layoutManager?.onRestoreInstanceState(recyclerViewState)
                    }
                }
            }
        }

        binding.fullScheduleButton.setOnClickListener {
            val browserIntent: Intent = Intent(
                Intent.ACTION_VIEW, Uri.parse(
                    "https://t1.kakaocdn.net/inhouse_daglona/ifkakao_2022/static/prod/timetable.html"
                )
            )
            if (browserIntent.resolveActivity(requireContext().packageManager) != null) {
                startActivity(browserIntent)
            }
        }

        binding.filterButton.setOnClickListener {
            binding.filterDrawerLayout.open()
        }

        binding.sessionFilterDrawerMenu.filterResetButton.setOnClickListener {
            viewModel.resetFilter()
        }

        binding.floatingUpButton.setOnClickListener {
            binding.sessionRecyclerView.smoothScrollToPosition(0)
        }
    }

    private fun filterInitialize(
        filterListBinding: FilterListBinding,
        name: String,
        filterData: List<FilterType>
    ) {
        val filterListAdapter = FilterListAdapter(::onFilterChecked, ::onFilterUnChecked)
        filterListBinding.filterNameTextView.text = name
        filterListBinding.filterRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        filterListBinding.filterRecyclerView.adapter = filterListAdapter
        val filterList = filterData.filter { it.toString() != "" && it.toString() != "--" }
        filterListBinding.filterTotalCount.text = filterList.size.toString()
        filterListAdapter.submitList(filterList)
    }

    private fun setFilterCount(filterListBinding: FilterListBinding, size: Int) {
        if (size > 0) {
            filterListBinding.filterCountSlash.visibility = VISIBLE
            filterListBinding.filterSelectedCount.visibility = VISIBLE
            filterListBinding.filterSelectedCount.text = size.toString()
        } else {
            filterListBinding.filterCountSlash.visibility = GONE
            filterListBinding.filterSelectedCount.visibility = GONE
        }
    }

    fun onFilterChecked(filterType: FilterType) {
        when (filterType) {
            is SessionType -> {
                viewModel.checkType(filterType)
            }
            is Track -> {
                viewModel.checkTrack(filterType)
            }
            is Company -> {
                viewModel.checkCompany(filterType)
            }
        }
    }

    fun onFilterUnChecked(filterType: FilterType) {
        when (filterType) {
            is SessionType -> {
                viewModel.unCheckType(filterType)
            }
            is Track -> {
                viewModel.unCheckTrack(filterType)
            }
            is Company -> {
                viewModel.unCheckCompany(filterType)
            }
        }
    }
}