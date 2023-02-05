package com.example.ifkakao.presentation.presentation_session_list.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import com.example.ifkakao.databinding.FragmentSessionListBinding
import com.example.ifkakao.di.component.SessionListComponent
import com.example.ifkakao.domain.model.Session
import com.example.ifkakao.presentation.activity.MainActivity
import com.example.ifkakao.presentation.presentation_session_list.adapter.SessionGridAdapter
import com.example.ifkakao.presentation.presentation_session_list.viewmodel.SessionListFragmentViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class SessionListFragment : Fragment() {

    private var _binding: FragmentSessionListBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var fragmentComponent: SessionListComponent

    @Inject
    lateinit var viewModel: SessionListFragmentViewModel
    override fun onAttach(context: Context) {
        fragmentComponent = (activity as MainActivity).mainComponent.sessionListFragmentComponent().create()
        fragmentComponent.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSessionListBinding.inflate(inflater, container, false)

        val dataList = mutableListOf(
            Session(),
            Session(),
            Session(),
            Session(),
            Session(),
        )
        val adapter = SessionGridAdapter(dataList)
        binding.sessionList.adapter = adapter
        binding.sessionList.layoutManager = GridLayoutManager(activity, 2)
        adapter.list = dataList

        viewModel.load()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.sessionListUiState.collectLatest {
                    binding.sessionList.adapter = SessionGridAdapter(it.sessions.toMutableList())
                }
            }
        }
    }
}