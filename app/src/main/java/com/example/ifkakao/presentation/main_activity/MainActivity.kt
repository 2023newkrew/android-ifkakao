package com.example.ifkakao.presentation.main_activity

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.Gravity
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout.LOCK_MODE_LOCKED_CLOSED
import androidx.drawerlayout.widget.DrawerLayout.LOCK_MODE_LOCKED_OPEN
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.example.ifkakao.R
import com.example.ifkakao.databinding.ActivityMainBinding
import com.example.ifkakao.databinding.FragmentMainBinding
import com.example.ifkakao.di.MyApplication
import com.example.ifkakao.di.component.MainComponent
import com.example.ifkakao.domain.model.Session
import com.example.ifkakao.presentation.detail_session.DetailSessionFragment
import com.example.ifkakao.presentation.home.fragment.HomeFragment
import com.example.ifkakao.presentation.session_list.fragment.SessionListFragment
import javax.inject.Inject

class MainActivity
    : AppCompatActivity(), MainActivityListener {

    @Inject
    lateinit var mainComponent: MainComponent

    @Inject
    lateinit var viewModel: MainActivityViewModel

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    var nowCode = MainActivityListener.Code.HOME
    var nowSession = Session()

    override fun onCreate(savedInstanceState: Bundle?) {
        mainComponent = (application as MyApplication).appComponent.mainComponent().create()
        mainComponent.inject(this)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        super.onCreate(savedInstanceState)
        setContentView(view)

        if (savedInstanceState != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                nowCode = (savedInstanceState.getSerializable("code", MainActivityListener.Code::class.java)) ?: MainActivityListener.Code.HOME
                nowSession = savedInstanceState.getSerializable("session", Session::class.java) ?: Session()
            } else {
                nowCode = (savedInstanceState.getSerializable("code") as? MainActivityListener.Code) ?: MainActivityListener.Code.HOME
                nowSession = (savedInstanceState.getSerializable("session") as? Session) ?: Session()
            }
        }
        goToFragment(nowCode)

        binding.toolbarTextTitle.setOnClickListener {
            goToFragment(MainActivityListener.Code.HOME)
            binding.drawerLayout.setDrawerLockMode(LOCK_MODE_LOCKED_CLOSED)
        }
        binding.drawerMenuOpenButton.setOnClickListener {
            binding.drawerLayout.openDrawer(GravityCompat.START)
            binding.drawerLayout.setDrawerLockMode(LOCK_MODE_LOCKED_OPEN)
        }
        binding.drawerTextSession.setOnClickListener {
            goToFragment(MainActivityListener.Code.SESSION_LIST)
            binding.drawerLayout.setDrawerLockMode(LOCK_MODE_LOCKED_CLOSED)
        }
        binding.drawerTextCoc.setOnClickListener {
            val browserIntent = Intent(
                Intent.ACTION_VIEW, Uri.parse(
                    "https://mk.kakaocdn.net/dn/if-kakao/2022/if_kakao_code_of_conduct_v1.1.pdf"
                )
            )
            startActivity(browserIntent)
        }
        binding.drawerMenuCloseButton.setOnClickListener {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
            binding.drawerLayout.setDrawerLockMode(LOCK_MODE_LOCKED_CLOSED)
        }

    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putSerializable("code", nowCode)
        outState.putSerializable("session", nowSession)
        super.onSaveInstanceState(outState)
    }

    override fun goToFragment(code: MainActivityListener.Code, session: Session) {
        nowCode = code
        if (session != Session()) {
            nowSession = session
        }
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            when (code) {
                MainActivityListener.Code.HOME -> {
                    replace<HomeFragment>(R.id.main_fragment_container_view)
                }
                MainActivityListener.Code.SESSION_LIST -> {
                    replace<SessionListFragment>(R.id.main_fragment_container_view)
                }
                MainActivityListener.Code.DETAIL_SESSION -> {
                    val bundle = Bundle()
                    bundle.putSerializable("session", nowSession)
                    val detailSessionFragment = DetailSessionFragment()
                    detailSessionFragment.arguments = bundle
                    replace(R.id.main_fragment_container_view, detailSessionFragment)
                }
            }
        }
    }
}