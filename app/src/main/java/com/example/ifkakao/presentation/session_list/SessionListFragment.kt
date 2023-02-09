package com.example.ifkakao.presentation.session_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.ifkakao.R
import com.example.ifkakao.databinding.FragmentSessionListBinding
import com.example.ifkakao.domain.model.Session
import com.example.ifkakao.presentation.MainActivity
import com.google.accompanist.appcompattheme.AppCompatTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SessionListFragment : Fragment() {

    private var _binding: FragmentSessionListBinding? = null
    private val binding get() = _binding!!

    private lateinit var navController: NavController
    private val viewModel: SessionListViewModel by viewModels()
    private lateinit var sessionListAdapter: SessionListAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSessionListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        navController = view.findNavController()

        sessionListAdapter = SessionListAdapter(
            onItemClick = ::onItemClick,
            onLikeClick = ::onItemLike
        )
        binding.sessionListGridView.layoutManager = GridLayoutManager(view.context, 2)
        binding.sessionListGridView.adapter = sessionListAdapter

        binding.sessionRadioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.session_radio_button_1 -> viewModel.dateSelected(0)
                R.id.session_radio_button_2 -> viewModel.dateSelected(1)
                R.id.session_radio_button_3 -> viewModel.dateSelected(2)
                R.id.session_radio_button_4 -> viewModel.dateSelected(3)
            }
            //TODO: 날짜 클릭시 recyclerview가 최상단으로 올라가지 않는 문제
            binding.sessionListGridView.smoothScrollToPosition(0)
        }

        binding.drawerComposeView.setContent {
            AppCompatTheme {
                FilterLayout(
                    viewModel, getString(R.string.app_name)
                ) { binding.sessionListDrawerLayout.closeDrawer(GravityCompat.START) }
            }
        }


        binding.sessionFilterButton.setOnClickListener {
            binding.sessionListDrawerLayout.openDrawer(GravityCompat.START)
            ((requireActivity()) as MainActivity).hideToolbar()
        }



        binding.sessionListDrawerLayout.addDrawerListener(
            object : DrawerLayout.DrawerListener {
                override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
                }

                override fun onDrawerOpened(drawerView: View) {
                    (requireActivity() as MainActivity).hideToolbar()
                }

                override fun onDrawerClosed(drawerView: View) {
                    (requireActivity() as MainActivity).showToolbar()
                }

                override fun onDrawerStateChanged(newState: Int) {
                }

            }
        )


        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.showSessionList.collectLatest {
                    sessionListAdapter.submitList(it.toMutableList())
                    binding.sessionNumberText.text = it.size.toString()
                }
            }
        }

        super.onViewCreated(view, savedInstanceState)
    }


    private fun onItemClick(session: Session) {
        val args = Bundle().apply {
            putParcelable(
                "Session",
                session
            )
        }
        navController.navigate(R.id.session_detail_fragment, args = args)
    }

    private fun onItemLike(session: Session) {
        viewModel.likeToggle(session.id)
    }

}

@Composable
fun FilterLayout(
    viewModel: SessionListViewModel,
    appName: String,
    onCloseClick: () -> (Unit)
) {
    val filterInfo by viewModel.filterItems.collectAsState()
    Column(
        Modifier
            .fillMaxSize()
            .padding(10.dp)
            .verticalScroll(rememberScrollState())

    ) {
        Row {
            Text(
                text = appName,
                color = Color.White,
                fontSize = 20.sp,
                modifier = Modifier.padding(10.dp)
            )
            Spacer(modifier = Modifier.weight(1f))

            IconButton(
                onClick = onCloseClick
            ) {
                Icon(
                    imageVector = Icons.Filled.Close,
                    tint = Color.White,
                    contentDescription = "close button"
                )
            }
        }
        Row {
            Spacer(modifier = Modifier.weight(1f))
            IconButton(onClick = { viewModel.filterReset() }) {
                Icon(
                    imageVector = Icons.Filled.Refresh,
                    tint = if (filterInfo.count() > 0)
                        Color.Cyan
                    else Color.Gray,
                    contentDescription = "reset button"
                )
            }
            ClickableText(
                text = AnnotatedString(text = "초기화"),
                onClick = { viewModel.filterReset() },
                style = TextStyle(
                    color = if (filterInfo.count() > 0)
                        Color.Cyan
                    else Color.Gray
                ),
                modifier = Modifier.align(Alignment.CenterVertically)
            )

        }
        FilterTitle(
            title = "유형",
            num = 3,
            filterInfo = filterInfo,
            checkCount = { filterInfo.countType() })

        CheckBoxWrapper(
            id = 0,
            string = "키노트",
            boolean = filterInfo.isKeynote,
            viewModel = viewModel
        )
        CheckBoxWrapper(
            id = 1,
            string = "프리뷰",
            boolean = filterInfo.isPreview,
            viewModel = viewModel
        )
        CheckBoxWrapper(
            id = 2,
            string = "기술세션",
            boolean = filterInfo.isTechSession,
            viewModel = viewModel
        )

        Spacer(modifier = Modifier.height(10.dp))
        Spacer(
            modifier = Modifier
                .height(1.dp)
                .fillMaxWidth()
                .border(1.dp, Color.Gray)
        )
        Spacer(modifier = Modifier.height(10.dp))

        FilterTitle(
            title = "트랙",
            num = 12,
            filterInfo = filterInfo,
            checkCount = { filterInfo.countTrack() })

        CheckBoxWrapper(
            id = 3,
            string = "1015장애 회고",
            boolean = filterInfo.is1015,
            viewModel = viewModel
        )
        CheckBoxWrapper(id = 4, string = "Ai", boolean = filterInfo.isAi, viewModel = viewModel)
        CheckBoxWrapper(id = 5, string = "벡엔드", boolean = filterInfo.isBe, viewModel = viewModel)
        CheckBoxWrapper(id = 6, string = "프론트엔드", boolean = filterInfo.isFe, viewModel = viewModel)
        CheckBoxWrapper(
            id = 7,
            string = "모바일",
            boolean = filterInfo.isMobile,
            viewModel = viewModel
        )
        CheckBoxWrapper(
            id = 8,
            string = "클라우드",
            boolean = filterInfo.isCloud,
            viewModel = viewModel
        )
        CheckBoxWrapper(
            id = 9,
            string = "빅데이터",
            boolean = filterInfo.isBigData,
            viewModel = viewModel
        )
        CheckBoxWrapper(
            id = 10,
            string = "블록체인",
            boolean = filterInfo.isBlockChain,
            viewModel = viewModel
        )
        CheckBoxWrapper(
            id = 11,
            string = "DevOps",
            boolean = filterInfo.isDevOps,
            viewModel = viewModel
        )
        CheckBoxWrapper(id = 12, string = "ESG", boolean = filterInfo.isESG, viewModel = viewModel)
        CheckBoxWrapper(
            id = 13,
            string = "General",
            boolean = filterInfo.isGeneral,
            viewModel = viewModel
        )
        CheckBoxWrapper(
            id = 14,
            string = "Culture",
            boolean = filterInfo.isCulture,
            viewModel = viewModel
        )
        Spacer(
            modifier = Modifier
                .height(1.dp)
                .fillMaxWidth()
                .border(1.dp, Color.Gray)
        )
        Spacer(modifier = Modifier.height(10.dp))

        FilterTitle(
            title = "소속",
            num = 10,
            filterInfo = filterInfo,
            checkCount = { filterInfo.countCompany() })

        CheckBoxWrapper(
            id = 15,
            string = "카카오",
            boolean = filterInfo.isKakao,
            viewModel = viewModel
        )
        CheckBoxWrapper(
            id = 16,
            string = "카카오페이",
            boolean = filterInfo.isKakaoPay,
            viewModel = viewModel
        )
        CheckBoxWrapper(
            id = 17,
            string = "카카오엔터프라이즈",
            boolean = filterInfo.isKakaoEnterprise,
            viewModel = viewModel
        )
        CheckBoxWrapper(
            id = 18,
            string = "카카오모빌리티",
            boolean = filterInfo.isKakaoMobility,
            viewModel = viewModel
        )
        CheckBoxWrapper(
            id = 19,
            string = "카카오뱅크",
            boolean = filterInfo.isKakaoBank,
            viewModel = viewModel
        )
        CheckBoxWrapper(
            id = 20,
            string = "카카오브레인",
            boolean = filterInfo.isKakaoBrain,
            viewModel = viewModel
        )
        CheckBoxWrapper(
            id = 21,
            string = "카카오게임즈",
            boolean = filterInfo.isKakaoGames,
            viewModel = viewModel
        )
        CheckBoxWrapper(
            id = 22,
            string = "카카오엔터테인먼트",
            boolean = filterInfo.isKakaoEntertainment,
            viewModel = viewModel
        )
        CheckBoxWrapper(
            id = 23,
            string = "크러스틑 유니버스",
            boolean = filterInfo.isKrustUniverse,
            viewModel = viewModel
        )
        CheckBoxWrapper(
            id = 24,
            string = "카카오픽코마",
            boolean = filterInfo.isKakaoPickoma,
            viewModel = viewModel
        )
    }
}

@Composable
fun checkBokClick(): CheckboxColors {
    return CheckboxDefaults.colors(
        checkedColor = Color.Cyan,
        uncheckedColor = Color.LightGray
    )
}

@Composable
fun FilterTitle(
    title: String,
    num: Int,
    filterInfo: SessionListFilterItems,
    checkCount: () -> (Int)
) {
    Spacer(modifier = Modifier.height(10.dp))
    Row {
        Text(
            text = title,
            color = Color.White,
            fontSize = 18.sp,
            modifier = Modifier
                .padding(10.dp)
                .align(Alignment.CenterVertically)
        )
        Text(
            text = if (checkCount() > 0)
                checkCount().toString()
            else "",
            color = Color.Cyan,
            fontSize = 18.sp,
            modifier = Modifier
                .align(Alignment.CenterVertically)
        )
        Text(
            text = if (checkCount() > 0)
                "/$num"
            else num.toString(),
            color = Color.Gray,
            fontSize = 18.sp,
            modifier = Modifier
                .align(Alignment.CenterVertically)
        )
    }
}


@Composable
fun CheckBoxWrapper(id: Int, string: String, boolean: Boolean, viewModel: SessionListViewModel) {
    Row {
        Checkbox(
            checked = boolean,
            onCheckedChange = {
                viewModel.filterItemChanged(id, !boolean)
            },
            colors = checkBokClick(),
            enabled = true
        )
        Text(
            text = string,
            color = Color.Gray,
            fontSize = 15.sp,
            modifier = Modifier
                .padding(5.dp)
                .align(Alignment.CenterVertically)
        )
    }
}