package com.example.ifkakao.presentation.session_detail

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.example.ifkakao.R
import com.example.ifkakao.databinding.FragmentSessionDetailBinding
import com.example.ifkakao.databinding.FragmentSessionListBinding
import com.example.ifkakao.presentation.session_list.SessionListViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SessionDetailFragment : Fragment() {

    private var _binding: FragmentSessionDetailBinding? = null
    private val binding get() = _binding!!

    private lateinit var navController: NavController
    private val viewModel: SessionDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSessionDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        navController = view.findNavController()
        val dateList = listOf("2022.12.07", "2022.12.08", "2022.12.09")

        binding.sessionDetailTitle.text = viewModel.session.title
        binding.sessionDetailGroup.text = viewModel.session.company.toString()
        binding.sessionDetailKeynote.text = viewModel.session.type.toString()
        binding.sessionDetailDate.text = dateList[viewModel.session.sessionDay]
        binding.sessionDetailTags.text = viewModel.session.tags
        binding.sessionDetailBody.text = viewModel.session.description
        binding.sessionDetailNickName.text = viewModel.session.users.joinToString { it.id + " " }
        binding.sessionDetailNickNameDetail.text =
            viewModel.session.users.joinToString { it.intro + "\n" }

        binding.sessionDetailBackButton.setOnClickListener {
            navController.navigateUp()
        }
        binding.sessionDetailShareButton.setOnClickListener {
            val clipboardManager: ClipboardManager = requireContext().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clipData: ClipData = ClipData.newPlainText("clip", viewModel.session.sessionVodLink)
            clipboardManager.setPrimaryClip(clipData)
        }

        super.onViewCreated(view, savedInstanceState)
    }
}