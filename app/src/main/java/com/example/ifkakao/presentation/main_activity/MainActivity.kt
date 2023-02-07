package com.example.ifkakao.presentation.main_activity

import android.os.Build
import android.os.Bundle
import android.view.Gravity
import android.view.View
import androidx.appcompat.app.AppCompatActivity
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
        goToFragment(nowCode, nowSession)

        binding.drawerMenuOpenButton.setOnClickListener(View.OnClickListener {
            binding.drawerLayout.openDrawer(Gravity.LEFT)
            binding.drawerLayout.setDrawerLockMode(LOCK_MODE_LOCKED_OPEN)
        })
        binding.drawerMenuCloseButton.setOnClickListener(View.OnClickListener {
            binding.drawerLayout.closeDrawer(Gravity.LEFT)
            binding.drawerLayout.setDrawerLockMode(LOCK_MODE_LOCKED_CLOSED)
        })

    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putSerializable("code", nowCode)
        outState.putSerializable("session", nowSession)
        super.onSaveInstanceState(outState)
    }

    override fun goToFragment(code: MainActivityListener.Code, session: Session) {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            nowCode = code
            when (code) {
                MainActivityListener.Code.HOME -> {
                    replace<HomeFragment>(R.id.main_fragment_container_view)
                }
                MainActivityListener.Code.SESSION_LIST -> {
                    replace<SessionListFragment>(R.id.main_fragment_container_view)
                }
                MainActivityListener.Code.DETAIL_SESSION -> {
                    nowSession = session
                    val bundle = Bundle()
                    bundle.putSerializable("test", session)
                    val detailSessionFragment = DetailSessionFragment()
                    detailSessionFragment.arguments = bundle
                    replace(R.id.main_fragment_container_view, detailSessionFragment)
                }
            }
        }
    }
}