package com.example.ifkakao.presentation.session

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.ifkakao.R
import com.example.ifkakao.databinding.FragmentSessionBinding
import com.example.ifkakao.presentation.session.select.SessionSelectFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SessionFragment : Fragment() {
    private var _binding: FragmentSessionBinding? = null
    private val binding get() = _binding!!

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

        // initialize fragment container view
        if (savedInstanceState == null) {
            val sessionSelectFragment = SessionSelectFragment()
            arguments?.let { sessionSelectFragment.arguments = it }
            parentFragmentManager.beginTransaction()
                .setReorderingAllowed(true)
                .add(R.id.session_fragment_container, sessionSelectFragment)
                .commit()
        }
    }
}