package com.example.ifkakao.presentation.main

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.ifkakao.R
import com.example.ifkakao.databinding.ActivityMainBinding
import com.example.ifkakao.databinding.FragmentMainBinding

class MainFragment : Fragment() {
    private val binding: FragmentMainBinding by lazy { FragmentMainBinding.inflate(layoutInflater) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.nestedScrollView.setOnScrollChangeListener { _, _, scrollY, _, _ ->
            // set status bar color
            requireActivity().window.statusBarColor = requireContext().getColor(
                if (scrollY == 0) R.color.deepskyblue
                else R.color.black_80
            )
            // set action bar color
            (requireActivity() as AppCompatActivity).supportActionBar?.setBackgroundDrawable(
                ColorDrawable(
                    if (scrollY == 0) Color.TRANSPARENT
                    else ContextCompat.getColor(requireContext(), R.color.black_80)
                )
            )
        }
    }


}