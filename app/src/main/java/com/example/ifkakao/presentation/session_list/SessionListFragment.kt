package com.example.ifkakao.presentation.session_list

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.example.ifkakao.data.data_source.remote.dto.ResultSession
import com.example.ifkakao.databinding.FragmentSessionListBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SessionListFragment : Fragment() {

    private var _binding: FragmentSessionListBinding? = null
    private val binding get() = _binding!!

    private lateinit var navController: NavController
    private val Fragment.packageManager get() = activity?.packageManager
    private val viewModel: SessionListViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSessionListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val sessionListAdapter = SessionListAdapter(
            onItemClick = ::onItemClick,
            onLikeClick = ::onItemLike
        )
        binding.sessionListGridView.adapter = sessionListAdapter

        super.onViewCreated(view, savedInstanceState)
    }


    fun onItemClick(session: ResultSession) {

    }

    fun onItemLike(session: ResultSession) {

    }


}