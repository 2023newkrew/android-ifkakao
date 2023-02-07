package com.example.ifkakao.presentation.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ifkakao.R
import com.example.ifkakao.databinding.FragmentDetailBinding
import com.example.ifkakao.presentation.detail.adapter.UserListAdapter

class DetailFragment : Fragment() {
    private val args: DetailFragmentArgs by navArgs()
    private val binding: FragmentDetailBinding by lazy {
        FragmentDetailBinding.inflate(
            layoutInflater
        )
    }
    private val viewModel: DetailViewModel by viewModels()
    private lateinit var userListAdapter: UserListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding.toolbar.setTitle(R.string.label_list)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userListAdapter = UserListAdapter()
        val recyclerView = binding.userRecylerView
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = userListAdapter
        userListAdapter.submitList(args.sessionInfo.users)
        binding.contentsNestedScrollView.isNestedScrollingEnabled = false

        val tracks = args.sessionInfo.track.map {
            it.toString()
        }
        binding.titleTextView.text = args.sessionInfo.title
        binding.companyTextView.text = args.sessionInfo.company.toString()
        binding.typeTextView.text =
            args.sessionInfo.sessionType.toString() + tracks.joinToString(separator = "\t\t")
        binding.contentTextView.text = args.sessionInfo.description
        if (args.sessionInfo.ppt != "") {
            binding.pptButton.visibility = VISIBLE
            val pptUrl = args.sessionInfo.ppt
            binding.pptButton.setOnClickListener {
                val browserIntent: Intent = Intent(
                    Intent.ACTION_VIEW, Uri.parse(pptUrl)
                )
                if (browserIntent.resolveActivity(requireContext().packageManager) != null) {
                    startActivity(browserIntent)
                }
            }
        }
        //binding.videoWebView.loadUrl(args.sessionInfo.sessionVodLink)
    }


}