package com.example.ifkakao.presentation.presentation_session_list.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.ifkakao.R
import com.example.ifkakao.di.component.SessionListComponent
import com.example.ifkakao.presentation.activity.MainActivity
import com.example.ifkakao.presentation.presentation_session_list.viewmodel.SessionListFragmentViewModel
import javax.inject.Inject

class SessionListFragment : Fragment() {

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
    ): View? {
        viewModel.load()

        return inflater.inflate(R.layout.fragment_session_list, container, false)
    }
}