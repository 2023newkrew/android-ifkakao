package com.example.ifkakao.presentation.main

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context.CLIPBOARD_SERVICE
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.example.ifkakao.R
import com.example.ifkakao.databinding.FragmentMainBinding
import com.example.ifkakao.presentation.KakaoCorpURi
import com.example.ifkakao.presentation.MainVideoURI
import com.example.ifkakao.presentation.session_list.SessionListFilterItems
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainFragment : Fragment(R.layout.fragment_main) {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private lateinit var navController: NavController
    private val Fragment.packageManager get() = activity?.packageManager
    private val viewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        navController = view.findNavController()
        val videoView = binding.mainVideoView

        val uri = Uri.parse(MainVideoURI)
        videoView.setVideoURI(uri)

        videoView.setOnPreparedListener {
            it.isLooping = true
            it.start()
        }

        binding.mainTotalSessionButton.setOnClickListener {
            val args = Bundle().apply {
                putParcelable(
                    "FilterItems",
                    SessionListFilterItems()
                )
            }
            navController.navigate(R.id.session_list_fragment, args)
        }

        binding.mainKeynoteImage.setOnClickListener {
            val args = Bundle().apply {
                putParcelable(
                    "FilterItems",
                    SessionListFilterItems(isKeynote = true)
                )
            }
            navController.navigate(R.id.session_list_fragment, args)
        }

        binding.mainTechImage.setOnClickListener {
            val args = Bundle().apply {
                putParcelable(
                    "FilterItems",
                    SessionListFilterItems(isTechSession = true)
                )
            }
            navController.navigate(R.id.session_list_fragment, args)
        }

        binding.mainEthicsImage.setOnClickListener {
            val args = Bundle().apply {
                putParcelable(
                    "FilterItems",
                    SessionListFilterItems(isESG = true)
                )
            }
            navController.navigate(R.id.session_list_fragment, args)
        }

        binding.mainImageAi.setOnClickListener {
            val args = Bundle().apply {
                putParcelable(
                    "FilterItems",
                    SessionListFilterItems(isAi = true)
                )
            }
            navController.navigate(R.id.session_list_fragment, args)
        }

        binding.mainImageBe.setOnClickListener {
            val args = Bundle().apply {
                putParcelable(
                    "FilterItems",
                    SessionListFilterItems(isBe = true)
                )
            }
            navController.navigate(R.id.session_list_fragment, args)
        }

        binding.mainImageCl.setOnClickListener {
            val args = Bundle().apply {
                putParcelable(
                    "FilterItems",
                    SessionListFilterItems(isCloud = true)
                )
            }
            navController.navigate(R.id.session_list_fragment, args)
        }

        binding.mainImageDo.setOnClickListener {
            val args = Bundle().apply {
                putParcelable(
                    "FilterItems",
                    SessionListFilterItems(isDevOps = true)
                )
            }
            navController.navigate(R.id.session_list_fragment, args)
        }

        binding.mainImageBc.setOnClickListener {
            val args = Bundle().apply {
                putParcelable(
                    "FilterItems",
                    SessionListFilterItems(isBlockChain = true)
                )
            }
            navController.navigate(R.id.session_list_fragment, args)
        }

        binding.mainImageDt.setOnClickListener {
            val args = Bundle().apply {
                putParcelable(
                    "FilterItems",
                    SessionListFilterItems(isBigData = true)
                )
            }
            navController.navigate(R.id.session_list_fragment, args)
        }

        binding.mainImageFe.setOnClickListener {
            val args = Bundle().apply {
                putParcelable(
                    "FilterItems",
                    SessionListFilterItems(isFe = true)
                )
            }
            navController.navigate(R.id.session_list_fragment, args)
        }

        binding.mainImageM.setOnClickListener {
            val args = Bundle().apply {
                putParcelable(
                    "FilterItems",
                    SessionListFilterItems(isMobile = true)
                )
            }
            navController.navigate(R.id.session_list_fragment, args)
        }

        binding.mainLinkCopy.setOnClickListener {
            val clipboardManager: ClipboardManager = requireContext().getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
            val clipData: ClipData = ClipData.newPlainText("share", "https://if.kakao.com")
            clipboardManager.setPrimaryClip(clipData)
        }

        binding.mainSessionButton.setOnClickListener {
            val args = Bundle().apply {
                putParcelable(
                    "FilterItems",
                    SessionListFilterItems()
                )
            }
            navController.navigate(R.id.session_list_fragment, args)
        }

        binding.mainFooterCorp.setOnClickListener {
            viewModel.footerCorpClicked()

            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(KakaoCorpURi))
            packageManager?.let {
                if (intent.resolveActivity(packageManager!!) != null) {
                    startActivity(intent)
                }
            }
        }

        binding.mainFooterLastIfkakao.setOnClickListener {
            viewModel.footerLastKakaoClicked()

            //TODO: // View 새로 만들기
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.mainUiState.collectLatest {
                    if (it.isKakaoCorpClicked) {
                        binding.mainFooterCorp.setTextColor(
                            ContextCompat.getColor(
                                view.context,
                                R.color.session_blue
                            )
                        )
                    } else {
                        binding.mainFooterCorp.setTextColor(
                            ContextCompat.getColor(
                                view.context,
                                R.color.main_text_color
                            )
                        )
                    }
                    if (it.isLastIfKakaoClicked) {
                        binding.mainFooterLastIfkakao.setTextColor(
                            ContextCompat.getColor(
                                view.context,
                                R.color.session_blue
                            )
                        )
                    } else {
                        binding.mainFooterLastIfkakao.setTextColor(
                            ContextCompat.getColor(
                                view.context,
                                R.color.main_text_color
                            )
                        )
                    }
                }
            }
        }

        super.onViewCreated(view, savedInstanceState)
    }

}