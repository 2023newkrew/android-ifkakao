package com.example.ifkakao.presentation.session_list

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.navArgs
import com.example.ifkakao.R
import com.example.ifkakao.databinding.FragmentMainBinding
import com.example.ifkakao.databinding.FragmentSessionListBinding
import com.example.ifkakao.presentation.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SessionListFragment : Fragment() {
    private val filterItems: SessionListFilterItems by lazy {
        val args =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                arguments?.getSerializable("FilterItems", SessionListFilterItems::class.java)
            } else {
                arguments?.getSerializable("FilterItems") as SessionListFilterItems?
            }

        if (args != null) {
            viewModel.initFilterItems(args)
            args
        } else {
            viewModel.initFilterItems(SessionListFilterItems())
            SessionListFilterItems()
        }
    }

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
        viewModel.initFilterItems(filterItems)
        super.onViewCreated(view, savedInstanceState)
    }


}