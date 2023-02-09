package com.example.ifkakao.presentation.session_list

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextPainter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
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
import com.example.ifkakao.data.data_source.remote.dto.ResultSession
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
                val data = viewModel.filterItems.collectAsState()

                FilterLayout(data.value)
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


    @Composable
    fun FilterLayout(filters: SessionListFilterItems) {
        var checked by remember {
            mutableStateOf(filters.isKeynote)
        }
        Column(
            Modifier
                .fillMaxSize()
                .padding(10.dp)

        ) {
            Row {
                Text(
                    text = getString(R.string.app_name),
                    color = Color.White,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(10.dp)
                )
                Spacer(modifier = Modifier.weight(1f))

                IconButton(onClick = { binding.sessionListDrawerLayout.closeDrawer(GravityCompat.START) }
                ) {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        tint = Color.White,
                        contentDescription = "close button"
                    )
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            Spacer(modifier = Modifier
                .height(1.dp)
                .fillMaxWidth()
                .border(1.dp, Color.White))
            Spacer(modifier = Modifier.height(10.dp))
            Row {
                Text(
                    text = "유형",
                    color = Color.White,
                    fontSize = 18.sp,
                    modifier = Modifier
                        .padding(10.dp)
                        .align(Alignment.CenterVertically)
                )
                Text(
                    text = "3",
                    color = Color.Gray,
                    fontSize = 18.sp,
                    modifier = Modifier
                        .padding(5.dp)
                        .align(Alignment.CenterVertically)
                )
            }


            Row {
                Checkbox(
                    checked = checked,
                    onCheckedChange = {
                        checked = !checked
                    },
                    colors = checkBokClick(),
                    enabled = true
                )
                Text(
                    text = "키노트",
                    color = Color.Gray,
                    fontSize = 18.sp,
                    modifier = Modifier
                        .padding(5.dp)
                        .align(Alignment.CenterVertically)
                )
            }

        }
    }

    @Composable
    fun checkBokClick() : CheckboxColors{
        return CheckboxDefaults.colors(
            checkedColor = Color.Cyan,
            uncheckedColor = Color.LightGray
        )
    }
}