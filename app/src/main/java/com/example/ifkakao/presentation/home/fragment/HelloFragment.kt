package com.example.ifkakao.presentation.home.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import com.example.ifkakao.databinding.FragmentHelloBinding
import com.example.ifkakao.presentation.main_activity.MainActivityListener

class HelloFragment : Fragment(), OnClickListener {

    private var _binding: FragmentHelloBinding? = null
    private val binding get() = _binding!!

    private lateinit var parentListener: MainActivityListener

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is MainActivityListener) {
            parentListener = context
        } else {
            throw ClassCastException(
                context.toString()
                        + " must implement OnFragmentInteractionListener"
            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHelloBinding.inflate(inflater, container, false)

        binding.goToSessionListButton.setOnClickListener(this)
        return binding.root
    }

    override fun onClick(v: View?) {
        parentListener.goToFragment(MainActivityListener.Code.SESSION_LIST)
    }


}