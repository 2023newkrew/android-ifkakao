package com.example.ifkakao.presentation.detail_session

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.ifkakao.R
import com.example.ifkakao.domain.model.Session

class DetailSessionFragment : Fragment() {

    val session by lazy {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requireArguments().getSerializable("test", Session::class.java)
        } else {
            requireArguments().getSerializable("test") as? Session
        } ?: Session()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        println("allen1 : $session")
        return inflater.inflate(R.layout.fragment_detail_session, container, false)
    }
}