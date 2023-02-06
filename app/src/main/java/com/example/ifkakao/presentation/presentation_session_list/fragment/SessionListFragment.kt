package com.example.ifkakao.presentation.presentation_session_list.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import com.example.ifkakao.R
import com.example.ifkakao.databinding.FragmentSessionListBinding
import com.example.ifkakao.di.component.SessionListComponent
import com.example.ifkakao.domain.model.Session
import com.example.ifkakao.presentation.home.fragment.HomeFragment
import com.example.ifkakao.presentation.main_activity.MainActivity
import com.example.ifkakao.presentation.main_activity.MainActivityListener
import com.example.ifkakao.presentation.presentation_session_list.adapter.SessionGridAdapter
import com.example.ifkakao.presentation.presentation_session_list.viewmodel.SessionListFragmentViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class SessionListFragment : Fragment(), SessionListFragmentListener {

    private lateinit var onBackPressedCallback: OnBackPressedCallback

    private var _binding: FragmentSessionListBinding? = null
    private val binding get() = _binding!!

    private lateinit var parentListener: MainActivityListener

    @Inject
    lateinit var fragmentComponent: SessionListComponent

    @Inject
    lateinit var viewModel: SessionListFragmentViewModel
    override fun onAttach(context: Context) {
        fragmentComponent = (activity as MainActivity).mainComponent.sessionListFragmentComponent().create()
        fragmentComponent.inject(this)
        super.onAttach(context)
        if (context is MainActivityListener) {
            parentListener = context
        } else {
            throw ClassCastException(
                context.toString()
                        + " must implement OnFragmentInteractionListener"
            )
        }
        onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                parentFragmentManager.commit {
                    setReorderingAllowed(true)
                    replace<HomeFragment>(R.id.main_fragment_container_view)
                }
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSessionListBinding.inflate(inflater, container, false)

        val dataList = mutableListOf(Session())
        val adapter = SessionGridAdapter(dataList, this)
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
                    binding.sessionList.adapter = SessionGridAdapter(it.sessions.toMutableList(), this@SessionListFragment)
                }
            }
        }
    }

    override fun onDetach() {
        super.onDetach()
        onBackPressedCallback.remove()
    }

    override fun callBack(session: Session) {
        parentListener.callBack(MainActivityListener.Code.GO_TO_DETAIL_SESSION, session)
    }
}