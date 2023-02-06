package com.example.ifkakao.presentation.main

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.example.ifkakao.R
import com.example.ifkakao.databinding.FragmentMainBinding
import com.example.ifkakao.presentation.CocURI
import com.example.ifkakao.presentation.KakaoCorpURi
import com.example.ifkakao.presentation.MainVideoURI
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment(R.layout.fragment_main) {

    private var _binding: FragmentMainBinding?= null
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

        videoView.setOnPreparedListener{
            it.isLooping = true
            it.start()
        }

        binding.mainTotalSessionButton.setOnClickListener {
            navController.navigate(R.id.session_list_fragment)
        }

        binding.mainKeynoteImage.setOnClickListener{
            navController.navigate(R.id.session_list_fragment)
        }

        binding.mainTechImage.setOnClickListener{
            navController.navigate(R.id.session_list_fragment)
        }

        binding.mainEthicsImage.setOnClickListener{
            navController.navigate(R.id.session_list_fragment)
        }

        binding.mainImageAi.setOnClickListener {
            navController.navigate(R.id.session_list_fragment)
        }

        binding.mainImageBe.setOnClickListener{
            navController.navigate(R.id.session_list_fragment)
        }

        binding.mainImageCl.setOnClickListener{
            navController.navigate(R.id.session_list_fragment)
        }

        binding.mainImageDo.setOnClickListener{
            navController.navigate(R.id.session_list_fragment)
        }

        binding.mainImageBc.setOnClickListener{
            navController.navigate(R.id.session_list_fragment)
        }

        binding.mainImageDt.setOnClickListener{
            navController.navigate(R.id.session_list_fragment)
        }

        binding.mainImageFe.setOnClickListener{
            navController.navigate(R.id.session_list_fragment)
        }

        binding.mainImageM.setOnClickListener{
            navController.navigate(R.id.session_list_fragment)
        }

        binding.mainSessionButton.setOnClickListener {
            navController.navigate(R.id.session_list_fragment)
        }

        binding.mainFooterCorp.setOnClickListener {
            viewModel.footerCorpClicked()

            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(KakaoCorpURi))
            //더 좋은 방법이 없을까
            packageManager?.let{
                if(intent.resolveActivity(packageManager!!) != null){
                    startActivity(intent)
                }
            }
        }

        binding.mainFooterLastIfkakao.setOnClickListener {
            viewModel.footerLastKakaoClicked()

            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(KakaoCorpURi))
        }

        super.onViewCreated(view, savedInstanceState)
    }

}