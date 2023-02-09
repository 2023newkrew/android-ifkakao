package com.example.ifkakao.presentation.list

import android.content.Intent
import android.content.res.ColorStateList
import android.content.res.Configuration
import android.content.res.Resources
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
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import com.example.ifkakao.R
import com.example.ifkakao.databinding.FilterListBinding
import com.example.ifkakao.databinding.FragmentListBinding
import com.example.ifkakao.domain.model.*
import com.example.ifkakao.presentation.list.adapter.FilterListAdapter
import com.example.ifkakao.presentation.list.adapter.SessionListAdapter
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


// TODO: 좋아요 기능 추가
@AndroidEntryPoint
class ListFragment : Fragment() {
    private val args: ListFragmentArgs by navArgs()
    private val binding: FragmentListBinding by lazy { FragmentListBinding.inflate(layoutInflater) }
    private val viewModel: ListViewModel by viewModels()
    private lateinit var sessionListAdapter: SessionListAdapter
    private lateinit var layoutManager: GridLayoutManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding.toolbar.setTitle(R.string.label_list)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val r: Resources = Resources.getSystem()
        val config: Configuration = r.configuration
        layoutManager = if (config.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // 가로모드
            GridLayoutManager(requireContext(), 3)
        } else {
            // 세로모드
            GridLayoutManager(requireContext(), 2)
        }

        // recycler view, Adapter setting
        sessionListAdapter = SessionListAdapter(::onItemClick, ::onLikeClick)
        val recyclerView = binding.sessionRecyclerView
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = sessionListAdapter

        val animator: RecyclerView.ItemAnimator? = recyclerView.itemAnimator
        if (animator is SimpleItemAnimator) {
            (animator as SimpleItemAnimator?)?.supportsChangeAnimations = false
        }

        // drawer setting
        binding.filterDrawerNestedScrollView.isNestedScrollingEnabled = false
        val fa1 = filterInitialize(
            binding.sessionFilterDrawerMenu.typeFilter,
            "유형",
            SessionType.values().toList(),
            viewModel.sessionState.value.types,
        )
        val fa2 = filterInitialize(
            binding.sessionFilterDrawerMenu.trackFilter,
            "트랙",
            Track.values().toList(),
            viewModel.sessionState.value.tracks,
        )
        val fa3 = filterInitialize(
            binding.sessionFilterDrawerMenu.companyFilter,
            "소속",
            Company.values().toList(),
            viewModel.sessionState.value.companies,
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
                viewModel.getLikes().collect {
                    println("like 변경 탐지!")
                    viewModel.getSessions()
                    // TODO: getSessions 호출해서 sessionList 가 변경되었지만, 아래 collect 실행 안됨
                    //  그리고 notifyItemChanged(position) 넣어주면 해당 Item이 깜빡거림
                    //  notifyDataSetChanged()로 하면 깜빡 X -> 왜?
                    //sessionListAdapter.notifyDataSetChanged()
                }
            }
        }

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.sessionData.collect { state ->
                    println("sessionList 변경 탐지!")
                    val recyclerViewState = recyclerView.layoutManager?.onSaveInstanceState()
                    sessionListAdapter.submitList(state.sessionList.toMutableList()) {
                        recyclerView.layoutManager?.onRestoreInstanceState(recyclerViewState)
                        binding.sizeText.text = "${state.sessionList.size}"
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
            fa1.checkUpdate(viewModel.sessionState.value.types)
            fa2.checkUpdate(viewModel.sessionState.value.tracks)
            fa3.checkUpdate(viewModel.sessionState.value.companies)
        }

        binding.floatingUpButton.setOnClickListener {
            binding.sessionRecyclerView.smoothScrollToPosition(0)
        }

        // safe args로 넘겨준 값 바탕으로 세팅
        processArgs(args, fa1, fa2, fa3)
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            layoutManager.spanCount = 3
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            layoutManager.spanCount = 2
        }
    }

    private fun filterInitialize(
        filterListBinding: FilterListBinding,
        name: String,
        filterData: List<FilterType>,
        currentFilterData: Set<FilterType>,
    ): FilterListAdapter {
        val filterListAdapter =
            FilterListAdapter(::onFilterChecked, ::onFilterUnChecked, currentFilterData)
        filterListBinding.filterNameTextView.text = name
        filterListBinding.filterRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        filterListBinding.filterRecyclerView.adapter = filterListAdapter
        val filterList = filterData.filter { it.toString() != "" && it.toString() != "--" }
        filterListBinding.filterTotalCount.text = filterList.size.toString()
        filterListAdapter.submitList(filterList)
        return filterListAdapter
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

    fun onItemClick(position: Int) {
        val currentList = sessionListAdapter.currentList.toMutableList()
        val session = currentList[position]

        // 일단 모바일
        val action = ListFragmentDirections.actionListToDetail(session)
        binding.root.findNavController().navigate(action)
    }

    fun onLikeClick(position: Int) {
        val currentList = sessionListAdapter.currentList.toMutableList()
        val session = currentList[position]
        if (session.isLiked) {
            viewModel.sessionUnLike(session)
        } else {
            viewModel.sessionLike(session)
        }
        //sessionListAdapter.notifyItemChanged(position)
    }

    fun processArgs(
        args: ListFragmentArgs,
        fa1: FilterListAdapter,
        fa2: FilterListAdapter,
        fa3: FilterListAdapter,
    ) {
        if (args.sessionType != SessionType.Null) {
            viewModel.checkType(args.sessionType)
            fa1.checkUpdate(viewModel.sessionState.value.types)
        }
        if (args.sessionTrack != Track.Null) {
            viewModel.checkTrack(args.sessionTrack)
            fa2.checkUpdate(viewModel.sessionState.value.tracks)
        }
        if (args.sessionCompany != Company.Null) {
            viewModel.checkCompany(args.sessionCompany)
            fa3.checkUpdate(viewModel.sessionState.value.companies)
        }
    }
}